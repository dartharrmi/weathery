package com.dartharrmi.weathery.ui.home.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.CLEAR
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dartharrmi.weathery.R

abstract class SwipeToDeleteCallback(mContext: Context,
                                     private val backgroundColor: Int = Color.RED,
                                     @DrawableRes private val drawableResourceId: Int = R.drawable.ic_trash_can): Callback() {

    private val clearPaint: Paint = Paint().apply {
        xfermode = PorterDuffXfermode(CLEAR)
    }
    private val background: ColorDrawable = ColorDrawable()
    private val deleteDrawable: Drawable? = ContextCompat.getDrawable(mContext, drawableResourceId)
    private val intrinsicWidth: Int = deleteDrawable?.intrinsicWidth ?: 0
    private val intrinsicHeight: Int = deleteDrawable?.intrinsicHeight ?: 0

    override fun getSwipeThreshold(viewHolder: ViewHolder) = 0.7f

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder) = makeMovementFlags(0, ItemTouchHelper.LEFT)

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, viewHolder1: ViewHolder) = false

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val itemViewHeight = itemView.height
        val isCancelled = dX == 0f && !isCurrentlyActive

        if (isCancelled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        with(background) {
            color = backgroundColor
            setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            draw(c)
        }

        val deleteIconTop = itemView.top + (itemViewHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemViewHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        deleteDrawable?.let {
            it.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            it.draw(c)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) = c.drawRect(left, top, right, bottom, clearPaint)
}