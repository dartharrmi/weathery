package com.dartharrmi.weathery.ui.details

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.BaseFragment
import com.dartharrmi.weathery.databinding.FragmentCityDetailBinding
import com.dartharrmi.weathery.di.DependencyContainer
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.ui.livedata.Event
import com.dartharrmi.weathery.ui.livedata.Status.FAILURE
import com.dartharrmi.weathery.ui.livedata.Status.SUCCESS
import com.dartharrmi.weathery.utils.activityViewModelBuilder
import com.dartharrmi.weathery.utils.toStringWithoutScientificNotation
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_city_detail.*
import kotlinx.android.synthetic.main.fragment_city_detail.view.*

class CityDetailFragment: BaseFragment<FragmentCityDetailBinding>() {

    companion object {

        private const val MAP_ZOOM_LEVEL = 18.0f
    }

    private val viewModel: DetailsViewModel by activityViewModelBuilder {
        DetailsViewModel(DependencyContainer.downloadIconUseCase, DependencyContainer.saveBookmarkUseCase, DependencyContainer.deleteBookmarkUseCase)
    }

    private val args by navArgs<CityDetailFragmentArgs>()

    private val cityFoundObserver = Observer<Event<BitmapDrawable>> {
        onIconDownloadEvent(it)
    }

    private fun onIconDownloadEvent(event: Event<Drawable>) {
        when (event.status) {
            SUCCESS -> event.data?.let {
                dataBinding.root.locationDetailTitle.setCompoundDrawablesWithIntrinsicBounds(
                        null, null, it, null
                )
            }
            FAILURE -> Toast.makeText(requireContext(), event.throwable.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private val saveBookmarkObserver = Observer<Event<Boolean>> {
        onSaveBookmarkEvent(it)
    }

    private fun onSaveBookmarkEvent(event: Event<Boolean>) {
        when (event.status) {
            SUCCESS -> {
                args.locationDetail.bookmarked = true
                fabSaveBookmark.setImageResource(R.drawable.ic_bookmark_filled)
                Snackbar.make(fabSaveBookmark, R.string.bookmark_save, Snackbar.LENGTH_LONG).show()
            }

            FAILURE -> Snackbar.make(fabSaveBookmark, R.string.bookmark_save, Snackbar.LENGTH_LONG).show()
        }
    }

    private val deleteBookmarkObserver = Observer<Event<Boolean>> {
        onDeleteBookmarkEvent(it)
    }

    private fun onDeleteBookmarkEvent(event: Event<Boolean>) {
        when (event.status) {
            SUCCESS -> {
                args.locationDetail.bookmarked = false
                fabSaveBookmark.setImageResource(R.drawable.ic_bookmark_empty)
                Snackbar.make(fabSaveBookmark, R.string.bookmark_remove, Snackbar.LENGTH_LONG).show()
            }

            FAILURE -> Snackbar.make(fabSaveBookmark, R.string.bookmark_remove_error, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun getLayoutId() = R.layout.fragment_city_detail

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
        viewModel.downloadIconEvent.observe(this, cityFoundObserver)
        viewModel.saveBookmarkEvent.observe(this, saveBookmarkObserver)
        viewModel.deleteBookmarkEvent.observe(this, deleteBookmarkObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        dataBinding.cityBinder = LocationDetailViewBinder(args.locationDetail, requireContext())
        initGoogleMap()
        with(dataBinding.root) {
            fabSaveBookmark.setOnClickListener {
                if (!args.locationDetail.bookmarked) {
                    viewModel.saveBookmark(args.locationDetail)
                } else {
                    viewModel.deleteBookmark(args.locationDetail)
                }
            }
        }

        viewModel.downloadIcon(requireContext().resources, args.locationDetail.weatherIcon)
    }

    private fun initGoogleMap() {
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.detailImageHeader) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->
            with(googleMap) {
                uiSettings.setAllGesturesEnabled(false)

                animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                                LatLng(args.locationDetail.lat, args.locationDetail.lon),
                                MAP_ZOOM_LEVEL
                        )
                )
                addMarker(
                        MarkerOptions()
                                .position(LatLng(args.locationDetail.lat, args.locationDetail.lon))
                                .title(args.locationDetail.name)
                ).showInfoWindow()
            }
        }
    }
}

class LocationDetailViewBinder(private val city: CityWeather, private val context: Context, private val isImperial: Boolean = false) {

    fun getLocationTitle() = city.name

    fun getLocationTemperature() = context.getString(R.string.location_temperature_metric, city.temperature.toInt())

    fun getLocationWeather() = context.getString(R.string.location_weather, city.weatherHeader, city.weatherDescription)

    fun getLocationFeelsLike() = context.getString(R.string.location_feels_like_metric, city.feelsLike.toInt())

    fun getLocationHumidity() = context.getString(R.string.location_humidity, city.humidity.toInt())

    fun getLocationWind() = context.getString(
            R.string.location_wind_metric,
            city.windSpeed.toStringWithoutScientificNotation(2),
            city.windDegree.toStringWithoutScientificNotation(2)
    )

    fun isBookmarked() = city.bookmarked
}