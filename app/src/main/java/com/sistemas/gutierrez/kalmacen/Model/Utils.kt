package com.sistemas.gutierrez.kalmacen.Model

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sistemas.gutierrez.kalmacen.R

inline fun <reified T : Activity> Activity.goTo(data: Int? = 0) {
    val intent = Intent(this, T::class.java)
    if ( data != 0 ){
        intent.putExtra("data", data)
    }
    startActivity(intent)
    finish()
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

inline fun DrawerLayout.consume(f: () -> Unit): Boolean {
    f()
    closeDrawers()
    return true
}

inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun View.visible(){
    visibility = View.VISIBLE
}

fun View.desaparece(){
    visibility = View.GONE
}

//==========================================   CLASES DESLIZAMIENTO ITEMS   ====================================================

abstract class DeslizarCompleto(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val successIcon = ContextCompat.getDrawable(context, R.drawable.v_montacarga)
    private val intrinsicWidth = successIcon!!.intrinsicWidth
    private val intrinsicHeight = successIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backSuccessColor = Color.parseColor("#1D8E75")

    override fun onMove(recyclerView: RecyclerView,viewHolder: RecyclerView.ViewHolder,target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the green check background
        background.color = backSuccessColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        val successIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val successIconMargin = (itemHeight - intrinsicHeight) / 2
        val successIconLeft = itemView.right - successIconMargin - intrinsicWidth
        val successIconRight = itemView.right - successIconMargin
        val successIconBottom = successIconTop + intrinsicHeight

        // Draw the delete icon
        successIcon!!.setBounds(successIconLeft, successIconTop, successIconRight, successIconBottom)
        successIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}

abstract class DeslizarParcial(context: Context) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.v_pick)
    private val intrinsicWidth = deleteIcon!!.intrinsicWidth
    private val intrinsicHeight = deleteIcon!!.intrinsicHeight
    private val background = ColorDrawable()
    private val backDeleteColor = Color.parseColor("#AA1735")

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw the red delete background
        background.color = backDeleteColor
        background.setBounds(itemView.left , itemView.top, itemView.left + dX.toInt(), itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
        val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
        val deleteIconLeft = itemView.left + deleteIconMargin
        val deleteIconRight = itemView.left + deleteIconMargin + intrinsicWidth
        val deleteIconBottom = deleteIconTop + intrinsicHeight

        // Draw the delete icon
        deleteIcon!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
        deleteIcon.draw(c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}