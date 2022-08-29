package com.ahmetozaydin.logindemo

import android.content.Context
import android.graphics.Canvas
import android.media.MediaRouter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

abstract class SwipeGestures(context: Context) :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT ) {
    private  val favoriteColor = ContextCompat.getColor(context,R.color.color_red)
    private val blackcolor = ContextCompat.getColor(context,R.color.black)
    private val addIcon = R.drawable.vector_favorites
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        //take action for the swiped item
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        RecyclerViewSwipeDecorator.Builder(c,recyclerView,viewHolder,dX,dY,actionState,isCurrentlyActive)
            .addSwipeLeftActionIcon(addIcon)
            .addSwipeLeftBackgroundColor(favoriteColor)
            .addSwipeLeftLabel("Add to")
            .create()
            .decorate()
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}