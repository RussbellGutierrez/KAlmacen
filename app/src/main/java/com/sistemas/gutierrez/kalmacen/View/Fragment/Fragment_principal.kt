package com.sistemas.gutierrez.kalmacen.View.Fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.fragment_principal.*
import kotlinx.android.synthetic.main.fragment_principal.view.*
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.toast
import java.lang.Exception

class Fragment_principal : Fragment(),Observador , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var gv: View
    private var observable: Observable? = null
    private val TAG by lazy { Fragment_principal::class.java.simpleName }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Observable) {
            observable = context
        } else {
            throw RuntimeException("$context necesita implementar Observable")
        }
    }

    override fun onDetach() {
        super.onDetach()
        observable = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        gv = inflater.inflate(R.layout.fragment_principal, container, false)
        gv.tool_bar.title = when(Constant.titulo_bar) {
            "" -> resources.getString(R.string.app_name)
            else -> Constant.titulo_bar
        }
        (activity as AppCompatActivity).setSupportActionBar(gv.tool_bar)
        val version = "ver. ${BuildConfig.VERSION_NAME}"
        gv.txt_version.text = version
        val toggle = ActionBarDrawerToggle(activity, gv.drawer_layout, gv.tool_bar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        gv.drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        gv.nav_view.setNavigationItemSelectedListener(this)
        observable?.agregarObservador(this)
        return gv
    }

    override fun actualizar() {
        //Completar
    }

    override fun onNavigationItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.nav_inventario -> drawer_layout.consume {
            Constant.titulo_bar = "Inventario"
            transcFrag(Fragment_inventario())
        }
        R.id.nav_carga_general -> drawer_layout.consume {
            Constant.titulo_bar = "Carga General"
            transcFrag(Fragment_carga_general())
        }
        R.id.nav_carga_transporte -> drawer_layout.consume {
            Constant.titulo_bar = "Carga Transporte"
            transcFrag(Fragment_carga_transporte())
        }
        R.id.nav_sincronizar -> drawer_layout.consume { toast("En proceso") }
        R.id.nav_subir -> drawer_layout.consume { toast("42547841242") }
        R.id.nav_ajustes -> drawer_layout.consume {  }
        else -> { true }
    }

    private fun transcFrag(fragment: Fragment){
        (activity as AppCompatActivity).supportFragmentManager.inTransaction {
            replace(R.id.content_frame,fragment)
        }
        gv.tool_bar.title = Constant.titulo_bar
    }
}