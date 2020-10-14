package com.dartharrmi.weathery.ui.home.adapter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.CLEAR
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dartharrmi.weathery.R

abstract class SwipeToDeleteCallback(private val mContext: Context,
                                 private val mClearPaint: Paint = Paint(),
                                 private val mBackground: ColorDrawable = ColorDrawable(),
                                 private val backgroundColor: Int = Color.parseColor("#B80F0A"),
                                 private val deleteDrawable: Drawable? = ContextCompat.getDrawable(mContext, R.drawable.ic_trash),
                                 private val intrinsicWidth: Int? = deleteDrawable?.intrinsicWidth,
                                 private val intrinsicHeight: Int? = deleteDrawable?.intrinsicHeight): ItemTouchHelper.Callback() {

    companion object {

        private const val SWIPE_THRESHOLD = 0.7f
    }

    init {
        mClearPaint.xfermode = PorterDuffXfermode(CLEAR)
    }

    override fun getSwipeThreshold(viewHolder: ViewHolder) = SWIPE_THRESHOLD

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder) = makeMovementFlags(0, ItemTouchHelper.LEFT)

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder) = false

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val itemHeight = itemView.height

        val isCancelled = dX == 0f && !isCurrentlyActive

        if (isCancelled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        mBackground.run {
            color = backgroundColor
            setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
            draw(c)
        }

        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight!!) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth!!
        val deleteIconRight = itemView.right - deleteIconMargin
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        deleteDrawable?.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteDrawable?.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, mClearPaint)
    }
}