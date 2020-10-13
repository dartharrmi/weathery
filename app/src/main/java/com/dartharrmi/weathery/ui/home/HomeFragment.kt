package com.dartharrmi.weathery.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseApp
import com.dartharrmi.weathery.base.BaseFragment
import com.dartharrmi.weathery.databinding.FragmentHomeBinding
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.ui.livedata.Status
import com.dartharrmi.weathery.ui.map.MapFragment
import com.dartharrmi.weathery.utils.activityViewModelBuilder
import com.dartharrmi.weathery.utils.hideKeyBoard
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment: BaseFragment<FragmentHomeBinding>() {

    private val viewModel: HomeViewModel by activityViewModelBuilder {
        val di = (requireActivity().application as BaseApp).dependencyContainer
        HomeViewModel(di.findCitiesByNameUseCase, di.findCitiesByLocationUseCase)
    }

    private val cityFoundObserver = Observer<Event<CityWeather>> {
        onCityFoundEvent(it)
    }

    private val multipleCitiesFoundObserver = Observer<Event<List<CityWeather>>> {
        onMultipleCitiesFoundEvent(it)
    }

    private fun onCityFoundEvent(event: Event<CityWeather>) {
        when (event.status) {
            Status.SUCCESS -> Toast.makeText(
                    requireContext(),
                    event.data.toString(),
                    Toast.LENGTH_LONG
            ).show()
            Status.FAILURE -> Toast.makeText(
                    requireContext(),
                    event.throwable.toString(),
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun onMultipleCitiesFoundEvent(event: Event<List<CityWeather>>) {
        when (event.status) {
            Status.SUCCESS -> Toast.makeText(requireContext(), event.data.toString(), Toast.LENGTH_LONG).show()
            Status.FAILURE -> Toast.makeText(requireContext(), event.throwable.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun getLayoutId() = R.layout.fragment_home

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
        viewModel.isLoadingEvent.observe(this, isLoadingObserver)
        viewModel.cityFoundEvent.observe(this, cityFoundObserver)
        viewModel.multipleCitiesEvent.observe(this, multipleCitiesFoundObserver)
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
                // adapter = recipesAdapter
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
                    //this@CityListFragment.query = query.orEmpty()
                    //emptyState.gone()
                    viewModel.findCitiesByName(query.orEmpty())
                    requireActivity().hideKeyBoard()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })

            fabSearchMap.setOnClickListener {
                findNavController().navigate(R.id.dest_map_search)
            }
        }
    }
}