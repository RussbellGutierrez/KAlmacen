package com.sistemas.gutierrez.kalmacen.Controller

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sistemas.gutierrez.kalmacen.Controller.Volley.ServiceVolley
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.Model.DeslizarCompleto
import com.sistemas.gutierrez.kalmacen.Model.DeslizarParcial
import org.jetbrains.anko.toast

class Methods(private val context: Context?) {

    private val volley by lazy { ServiceVolley() }

    fun deslizarCompleto(recyclerView: RecyclerView){
        val swipeHandler = object : DeslizarCompleto(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Constant.adapdetalle?.accionDeslizar(0,viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    fun deslizarParcial(recyclerView: RecyclerView){
        val swipeHandler = object : DeslizarParcial(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Constant.adapdetalle?.accionDeslizar(1,viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}