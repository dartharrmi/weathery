package com.dartharrmi.weathery.ui.home.adapter

import android.content.Context
import com.dartharrmi.weathery.BR
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.adapter.BaseRecyclerViewAdapter
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.utils.toStringWithoutScientificNotation

class LocationResultsAdapter(private val context: Context,
                             private val results: List<CityWeather>,
                             private val onResultSelected: (selectedResultOption: CityWeather) -> Unit): BaseRecyclerViewAdapter(results) {

    override fun getItemCount() = results.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binder = LocationResultsViewBinder(context, results[position], onResultSelected)
        holder.bind(binder)
    }

    override fun itemLayoutId() = R.layout.item_location_result

    override fun itemToBindId() = BR.bookmarkViewBinder
}

class LocationResultsViewBinder(private val context: Context,
                                private val city: CityWeather,
                                private val onResultSelected: (selectedDocumentOption: CityWeather) -> Unit) {

    fun getBookmarkTitle() = city.name

    fun getBookmarkLatitude() = context.getString(R.string.bookmark_lat, city.lat.toStringWithoutScientificNotation(5))

    fun getBookmarkLongitude() = context.getString(R.string.bookmark_lon, city.lon.toStringWithoutScientificNotation(5))

    fun onBookmarkClicked() = onResultSelected.invoke(city)
}