package com.dartharrmi.weathery.ui.location_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseBottomDialogFragment
import com.dartharrmi.weathery.databinding.FragmentLocationResultListBinding
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.home.HomeFragmentDirections
import com.dartharrmi.weathery.ui.home.adapter.LocationResultsAdapter
import kotlinx.android.synthetic.main.fragment_location_result_list.view.*

class LocationListResultDialog: BaseBottomDialogFragment<FragmentLocationResultListBinding>() {

    companion object {

        const val ARG_RESULTS = "ARG_RESULTS"
        const val TAG = "LocationListResultDialog"

        fun newInstance(locationResults: List<CityWeather>): LocationListResultDialog {
            if (locationResults.isEmpty()) {
                throw IllegalArgumentException("Empty array")
            }

            val args = Bundle().apply {
                putParcelableArray(ARG_RESULTS, locationResults.toTypedArray())
            }

            return LocationListResultDialog().also {
                it.arguments = args
            }
        }
    }

    private lateinit var results: List<CityWeather>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            @Suppress("UNCHECKED_CAST")
            results = it.getParcelableArray(ARG_RESULTS)!!.toList() as List<CityWeather>
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        dataBinding.root.rvLocationResultList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = LocationResultsAdapter(requireContext(), results) {
                findNavController().navigate(HomeFragmentDirections.actionDestRecipeListToDestCityDetail(it))
            }
        }
    }

    override fun getLayoutId() = R.layout.fragment_location_result_list

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() = Unit
}