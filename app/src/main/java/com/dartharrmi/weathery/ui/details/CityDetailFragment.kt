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
import com.dartharrmi.weathery.base.BaseApp
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
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.UiSettings
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_city_detail.view.*

class CityDetailFragment: BaseFragment<FragmentCityDetailBinding>() {

    companion object {

        private const val MAP_ZOOM_LEVEL = 18.0f
    }

    private val viewModel: DetailsViewModel by activityViewModelBuilder {
        DetailsViewModel(DependencyContainer.downloadIconUseCase, DependencyContainer.saveBookmarkUseCase)
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

    override fun getLayoutId() = R.layout.fragment_city_detail

    override fun getVariablesToBind(): Map<Int, Any> = emptyMap()

    override fun initObservers() {
        viewModel.downloadIconEvent.observe(this, cityFoundObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)

        dataBinding.cityBinder = LocationDetailViewBinder(args.locationDetail, requireContext())
        initGoogleMap()
        with(dataBinding.root) {
            /*cardServings.text = getString(R.string.recipe_pill_srvings, args.recipeArg.servings.toString())
            cardReadyTime.text = getString(R.string.recipe_pill_cook_time, Utils.parseMinutes(args.recipeArg.readyInMinutes))
            args.recipeArg.nutrition.caloricBreakdown.run {
                recipeFat.text = getString(R.string.recipe_pill_fat, this.percentFat.toString())
                recipeCalories.text = getString(R.string.recipe_pill_cal, this.percentProtein.toString())
                recipeCarbs.text = getString(R.string.recipe_pill_carbs, this.percentCarbs.toString())

            }
            recipeIngredients.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = RecipeIngredientsAdapter(args.recipeArg.ingredients.map { ingredient -> ingredient.originalString })
            }
            recipeSteps.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = RecipeStepAdapter(args.recipeArg.analyzedInstructions)
            }*/
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