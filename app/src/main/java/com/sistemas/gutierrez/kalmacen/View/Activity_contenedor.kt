package com.sistemas.gutierrez.kalmacen.View

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import com.sistemas.gutierrez.kalmacen.Controller.Observable
import com.sistemas.gutierrez.kalmacen.Controller.Observador
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.Model.inTransaction
import com.sistemas.gutierrez.kalmacen.R
import com.sistemas.gutierrez.kalmacen.View.Fragment.Fragment_detalle
import com.sistemas.gutierrez.kalmacen.View.Fragment.Fragment_principal
import java.util.ArrayList

class Activity_contenedor: AppCompatActivity(), Observable {

    private var barcode = ""
    private var fragments: ArrayList<Observador> = ArrayList()
    private val TAG by lazy { Activity_contenedor::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contenedor)
        if (supportFragmentManager.findFragmentById(R.id.frame_contenedor) == null){
            supportFragmentManager.inTransaction {
                add(R.id.frame_contenedor,Fragment_principal())
            }
        }
    }

    override fun agregarObservador(o: Observador) {
        fragments.add(o)
    }

    override fun removerObservador(o: Observador) {
        fragments.remove(o)
    }

    override fun notificarObservador() {
        //Something to do
    }

    override fun onResume() {
        super.onResume()
        notificarObservador()
    }

    override fun dispatchKeyEvent(e: KeyEvent?): Boolean {
        if (Constant.respuesta_keyevent == 1) {
            if (e?.action == KeyEvent.ACTION_DOWN && e.keyCode != KeyEvent.KEYCODE_ENTER) {
                Log.w(TAG,">${e.unicodeChar.toChar()}< dispatchKeyEvent: $e")
                val pressedKey = e.unicodeChar.toChar()
                barcode += pressedKey
            }
            if (e?.action == KeyEvent.ACTION_DOWN && e.keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.e(TAG,"barcode-->>$barcode")
                barcode = ""
            }
        }
        return false
    }

    override fun onBackPressed() {
        if (Constant.detalle_visible) {
            supportFragmentManager.inTransaction {
                remove(Fragment_detalle())
            }
        }
        /*when(Constant.detalle_visible) {
            true -> supportFragmentManager.inTransaction {
                remove(Fragment_detalle())
            }
            false -> {}
        }*/
    }
}