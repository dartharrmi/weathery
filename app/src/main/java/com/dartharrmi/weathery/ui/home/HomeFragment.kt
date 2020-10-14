package com.dartharrmi.weathery.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseFragment
import com.dartharrmi.weathery.databinding.FragmentHomeBinding
import com.dartharrmi.weathery.di.DependencyContainer
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.home.adapter.BookmarksAdapter
import com.dartharrmi.weathery.ui.home.adapter.SwipeToDeleteCallback
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.ui.livedata.Status
import com.dartharrmi.weathery.ui.location_list.LocationListResultDialog
import com.dartharrmi.weathery.ui.map.MapFragment
import com.dartharrmi.weathery.utils.activityViewModelBuilder
import com.dartharrmi.weathery.utils.hideKeyBoard
import com.dartharrmi.weathery.utils.visible
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_city_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by activityViewModelBuilder {
        HomeViewModel(
                DependencyContainer.findCitiesByNameUseCase,
                DependencyContainer.findCitiesByLocationUseCase,
                DependencyContainer.getBookmarkUseCase
        )
    }
    private lateinit var adapter: BookmarksAdapter

    private val cityFoundObserver = Observer<Event<CityWeather>> {
        onCityFoundEvent(it)
    }

    private val multipleCitiesFoundObserver = Observer<Event<List<CityWeather>>> {
        onMultipleCitiesFoundEvent(it)
    }

    private fun onCityFoundEvent(event: Event<CityWeather>) {
        when (event.status) {
            Status.SUCCESS -> event.data?.let {
                findNavController().navigate(HomeFragmentDirections.actionDestRecipeListToDestCityDetail(it))
            }
            Status.FAILURE -> Toast.makeText(
                    requireContext(),
                    event.throwable.toString(),
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun onMultipleCitiesFoundEvent(event: Event<List<CityWeather>>) {
        when (event.status) {
            Status.SUCCESS -> {
                event.data?.let {
                    findNavController().navigate(HomeFragmentDirections.actionDestRecipeListToDestCityDetail(it[0]))
                }
            }

            Status.FAILURE -> Toast.makeText(requireContext(), event.throwable.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private val getBookmarkObserver = Observer<Event<List<CityWeather>>> {
        onGetBookmarkEvent(it)
    }

    private fun onGetBookmarkEvent(event: Event<List<CityWeather>>) {
        when (event.status) {
            Status.SUCCESS -> {
                if (event.data?.isNotEmpty() == true) {
                    adapter = BookmarksAdapter(requireContext(), event.data.toMutableList()) {
                        findNavController().navigate(HomeFragmentDirections.actionDestRecipeListToDestCityDetail(it))
                    }
                    rvBookmarksList.adapter = adapter
                } else {
                    emptyState.visible()
                }
            }

            Status.FAILURE -> Snackbar.make(fabSaveBookmark, R.string.bookmark_remove_error, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
        viewModel.isLoadingEvent.observe(this, isLoadingObserver)
        viewModel.cityFoundEvent.observe(this, cityFoundObserver)
        viewModel.multipleCitiesEvent.observe(this, multipleCitiesFoundObserver)
        viewModel.getBookmarksEvent.observe(this, getBookmarkObserver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parentFragmentManager.setFragmentResultListener(
                MapFragment.KEY_SELECTED_LOCATION,
                this,
                FragmentResultListener { requestKey, result ->
                    with(result.get(requestKey) as LatLng) {
                        viewModel.findCitiesByLocation(this.latitude, this.longitude)
                    }
                })
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        with(dataBinding.root) {
            rvBookmarksList.apply {
                layoutManager = LinearLayoutManager(getViewContext())
                addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                    ContextCompat.getDrawable(requireContext(), R.drawable.drawable_document_separator)?.let {
                        setDrawable(it)
                    }
                })

                viewTreeObserver
                        .addOnGlobalLayoutListener(object: OnGlobalLayoutListener {
                            override fun onGlobalLayout() {
                                rvBookmarksList.also {
                                    it.viewTreeObserver.removeOnGlobalLayoutListener(this)

                                    val appBarHeight: Int = this@with.appBarLayout.height
                                    it.translationY = -appBarHeight.toFloat()
                                    it.layoutParams.height = rvBookmarksList.height + appBarHeight
                                }
                            }
                        })
            }

            svSearchCity.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.findCitiesByName(query.orEmpty())
                    requireActivity().hideKeyBoard()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })

            fabSearchMap.setOnClickListener {
                findNavController().navigate(R.id.dest_map_search)
            }

            setUpSwipeToDeleteAndUndo()
        }

        viewModel.getBookmarks()
    }

    private fun setUpSwipeToDeleteAndUndo() {
        val swipeToDeleteCallback: SwipeToDeleteCallback = object: SwipeToDeleteCallback(requireContext()) {

            override fun onSwiped(viewHolder: ViewHolder, i: Int) {
                val position = viewHolder.bindingAdapterPosition
                val bookmark: CityWeather = adapter.get(position)
                adapter.remove(position)

                Snackbar.make(homeContainer, R.string.bookmark_remove_confirmation, Snackbar.LENGTH_LONG).apply {
                    setAction(R.string.bookmark_remove_action) {
                        adapter.restore(position, bookmark)
                        rvBookmarksList.scrollToPosition(position)
                    }
                    setActionTextColor(Color.YELLOW)
                }.show()
            }
        }

        val touchHelper = ItemTouchHelper(swipeToDeleteCallback)
        touchHelper.attachToRecyclerView(rvBookmarksList)
    }
}