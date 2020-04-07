package com.sistemas.gutierrez.kalmacen.View.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.sistemas.gutierrez.kalmacen.Controller.Methods
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.R
import kotlinx.android.synthetic.main.dialog_conteo.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class Dialog_conteo: DialogFragment() {

    private val metodos by lazy { Methods(context) }
    private lateinit var v: View
    private val TAG by lazy {Dialog_conteo::class.java.simpleName}

    override fun onCreateDialog(savedInstanceState: Bundle?) = dialog()

    @SuppressLint("InflateParams")
    private fun dialog(): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity?.layoutInflater
        v = inflater!!.inflate(R.layout.dialog_conteo,null)
        builder.setView(v)

        v.btn_cancelar.onClick {
            dismiss()
        }
        v.btn_editar.onClick {
            v.edt_cp.setText(v.edt_cp.hint)
            v.edt_cb.setText(v.edt_cb.hint)
            v.edt_cu.setText(v.edt_cu.hint)
        }
        v.btn_guardar.onClick {
            dismiss()
            toast("Guardado")
        }
        //Constant.touchdentro = false
        /*when(Constant.detalle){
            0 -> {
                v.linear_conteo.visible()
                v.linear_botones.visible()
            }
            1 -> {
                v.linear_ingresado.visible()
                v.linear_diferencia.visible()
            }
        }
        v.btn_cancelar.onClick {
            Constant.touchdentro = true
            dismiss()
            Constant.adapdetalle?.accionDeslizar(3,Constant.posicion)
        }
        v.btn_guardar.onClick {
            Constant.touchdentro = true
            dismiss()
            Constant.adapdetalle?.accionDeslizar(2,Constant.posicion)
        }*/
        return builder.create()
    }

    /*override fun onDestroy() {
        super.onDestroy()
        if (!Constant.touchdentro){
            Constant.adapdetalle?.accionDeslizar(4,0)
        }
    }*/
}