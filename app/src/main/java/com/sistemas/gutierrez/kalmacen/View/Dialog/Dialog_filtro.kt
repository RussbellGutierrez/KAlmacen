package com.sistemas.gutierrez.kalmacen.View.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sistemas.gutierrez.kalmacen.Controller.Detalle_adapter
import com.sistemas.gutierrez.kalmacen.Controller.Methods
import com.sistemas.gutierrez.kalmacen.R
import kotlinx.android.synthetic.main.dialog_camion.view.*

class Dialog_filtro: DialogFragment() {

    private val metodos by lazy { Methods(context) }
    private lateinit var v: View

    override fun onCreateDialog(savedInstanceState: Bundle?) = dialog()

    @SuppressLint("InflateParams")
    private fun dialog(): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        v = inflater!!.inflate(R.layout.dialog_filtro,null)
        builder.setView(v)
        return builder.create()
    }
}