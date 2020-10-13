package com.dartharrmi.weathery.ui.city_list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseFragment
import com.dartharrmi.weathery.databinding.FragmentCityListBinding
import com.dartharrmi.weathery.utils.activityViewModelBuilder
import com.dartharrmi.weathery.utils.hideKeyBoard
import kotlinx.android.synthetic.main.fragment_city_list.view.*

class CityListFragment : BaseFragment<FragmentCityListBinding>() {

    private val viewModel: HomeViewModel by activityViewModelBuilder {
        HomeViewModel()
    }

    override fun getLayoutId() = R.layout.fragment_city_list

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        with(dataBinding.root) {
            rvBookmarksList.apply {
                layoutManager = LinearLayoutManager(getViewContext())
                // adapter = recipesAdapter
                viewTreeObserver
                    .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
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

            //svSearchCity.queryHint = getString(R.string.search_view_hint)
            svSearchCity.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    //this@CityListFragment.query = query.orEmpty()
                    //emptyState.gone()
                    //search(query.orEmpty())
                    requireActivity().hideKeyBoard()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean = false
            })
        }
    }
}