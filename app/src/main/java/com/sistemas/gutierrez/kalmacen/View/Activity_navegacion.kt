package com.sistemas.gutierrez.kalmacen.View

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.sistemas.gutierrez.kalmacen.BuildConfig
import com.sistemas.gutierrez.kalmacen.Controller.Observable
import com.sistemas.gutierrez.kalmacen.Controller.Observador
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.Model.consume
import com.sistemas.gutierrez.kalmacen.Model.inTransaction
import com.sistemas.gutierrez.kalmacen.R
import com.sistemas.gutierrez.kalmacen.View.Fragment.Fragment_carga_general
import com.sistemas.gutierrez.kalmacen.View.Fragment.Fragment_carga_transporte
import com.sistemas.gutierrez.kalmacen.View.Fragment.Fragment_inventario
import kotlinx.android.synthetic.main.activity_navegacion.*
import org.jetbrains.anko.toast
import java.util.ArrayList

class Activity_navegacion: AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Observable {

    private var barcode = ""
    private var fragments: ArrayList<Observador> = ArrayList()
    private val TAG by lazy { Activity_navegacion::class.java.simpleName }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navegacion)
        setSupportActionBar(tool_bar)
        val version = "ver. ${BuildConfig.VERSION_NAME}"
        txt_version.text = version
        val toggle = ActionBarDrawerToggle(this, drawer_layout, tool_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        //tool_bar.title = resources.getString(R.string.app_name)
        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_inventario -> drawer_layout.consume { transcFrag(Fragment_inventario(),"Inventario") }
        R.id.nav_carga_general -> drawer_layout.consume { transcFrag(Fragment_carga_general(),"Carga General") }
        R.id.nav_carga_transporte -> drawer_layout.consume { transcFrag(Fragment_carga_transporte(),"Carga Transporte") }
        R.id.nav_sincronizar -> drawer_layout.consume { toast("En proceso") }
        R.id.nav_subir -> drawer_layout.consume { toast(barcode) }
        R.id.nav_ajustes -> drawer_layout.consume {  }
        else -> { true }
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
        return true
        //return super.dispatchKeyEvent(e)
    }

    private fun transcFrag(fragment: Fragment, title: String){
        supportFragmentManager.inTransaction {
            replace(R.id.content_frame,fragment)
        }
        tool_bar.title = title
    }
}