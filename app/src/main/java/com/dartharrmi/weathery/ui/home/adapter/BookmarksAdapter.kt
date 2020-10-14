package com.dartharrmi.weathery.ui.home.adapter

import android.content.Context
import com.dartharrmi.weathery.BR
import com.dartharrmi.weathery.R
import com.dartharrmi.weathery.base.adapter.BaseRecyclerViewAdapter
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.utils.toStringWithoutScientificNotation

class BookmarksAdapter(private val context: Context,
                       private val bookmarks: MutableList<CityWeather>,
                       private val onBookmarkSelected: (selectedDocumentOption: CityWeather) -> Unit): BaseRecyclerViewAdapter(bookmarks) {

    override fun getItemCount() = bookmarks.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val binder = BookmarkViewBinder(context, bookmarks[position], onBookmarkSelected)
        holder.bind(binder)
    }

    override fun itemLayoutId() = R.layout.item_bookmark

    override fun itemToBindId() = BR.bookmarkViewBinder

    fun remove(position: Int) {
        bookmarks.removeAt(position)
        notifyItemRemoved(position);
    }

    fun restore(position: Int, bookmark: CityWeather) {
        bookmarks.add(position, bookmark)
        notifyItemInserted(position)
    }

    fun get(position: Int) = bookmarks[position]
}

class BookmarkViewBinder(private val context: Context,
                         private val city: CityWeather,
                         private val onDocumentSelected: (selectedDocumentOption: CityWeather) -> Unit) {

    fun getBookmarkTitle() = city.name

    fun getBookmarkLatitude() = context.getString(R.string.bookmark_lat, city.lat.toStringWithoutScientificNotation(5))

    fun getBookmarkLongitude() = context.getString(R.string.bookmark_lon, city.lon.toStringWithoutScientificNotation(5))

    fun onBookmarkClicked() = onDocumentSelected.invoke(city)
}