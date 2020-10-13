package com.dartharrmi.weathery.ui.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseFragment
import com.dartharrmi.weathery.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.view.*

class MapFragment: BaseFragment<FragmentMapBinding>(), GoogleMap.OnMapClickListener {

    companion object {

        const val KEY_SELECTED_LOCATION = "KEY_SELECTED_LOCATION"

        private const val MAP_ZOOM_LEVEL = 18.0f
    }

    private lateinit var googleMap: GoogleMap
    private lateinit var selectedLocation: LatLng

    private fun initGoogleMap() {
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->
            this.googleMap = googleMap

            this.googleMap.setOnMapClickListener(this)
            this.googleMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            LatLng(0.0, 0.0),
                            MAP_ZOOM_LEVEL
                    )
            )
        }
    }

    override fun onMapClick(p0: LatLng?) {
        p0?.let {
            selectedLocation = it
            googleMap.addMarker(MarkerOptions().apply {
                position(it)
                title("Lat: ${p0.latitude} \n Lon: ${p0.longitude}")
            }).showInfoWindow()
        }
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        initGoogleMap()
        dataBinding.root.btnSearch.setOnClickListener {
            parentFragmentManager.setFragmentResult(KEY_SELECTED_LOCATION, bundleOf(KEY_SELECTED_LOCATION to selectedLocation))
            findNavController().popBackStack()
        }
    }

    override fun getLayoutId() = R.layout.fragment_map

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() = Unit
}