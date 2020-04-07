package com.sistemas.gutierrez.kalmacen.View.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.sistemas.gutierrez.kalmacen.Controller.*
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.Model.consume
import com.sistemas.gutierrez.kalmacen.R
import kotlinx.android.synthetic.main.fragment_inventario.view.*
import org.jetbrains.anko.support.v4.toast

class Fragment_inventario : Fragment(),Observador ,SearchView.OnQueryTextListener {

    private lateinit var gv: View
    private var observable: Observable? = null
    private val TAG by lazy { Fragment_inventario::class.java.simpleName }

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
        Constant.respuesta_keyevent = 1
        gv = inflater.inflate(R.layout.fragment_inventario, container, false)
        gv.recycler_inventario.layoutManager = LinearLayoutManager(activity)
        gv.recycler_inventario.hasFixedSize()
        gv.recycler_inventario.adapter =Inventario_adapter(activity!!)
        observable?.agregarObservador(this)
        gv.swipe_inventario.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN)
        gv.swipe_inventario.setOnRefreshListener {
            gv.swipe_inventario.isRefreshing = false
        }
        return gv
    }

    override fun actualizar() {
        //Completar
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.menu_inventario,menu)
        val search = menu.findItem(R.id.menu_busqueda).actionView as SearchView
        search.queryHint = "NOMBRE O CÓDIGO"
        search.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onQueryTextChange(newText: String) = false
    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.menu_escaner_celular -> consume { iniciarEscaner() }
        //R.id.menu_filtro -> consume { Dialog_filtro().show(activity!!.supportFragmentManager,"Dialog") }
        else -> super.onOptionsItemSelected(item)
    }

    private fun iniciarEscaner(){
        val escaner = IntentIntegrator.forSupportFragment(this)
        escaner.setPrompt("Enfoque el codigo del producto")
        escaner.setBeepEnabled(true)
        escaner.setOrientationLocked(false)
        escaner.initiateScan()
    }

    /*private fun escanerBluetooth(){
        val blue = BluetoothAdapter.getDefaultAdapter()
        if(!blue.isEnabled){
            val enable = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enable,0)
        }else{
            val intent = Intent()
            intent.action = "android.bluetooth.devicepicker.action.LAUNCH"
            startActivity(intent)
        }
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null){
            val scan: IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
            if (scan.contents.isNullOrEmpty() && scan.formatName.isNullOrEmpty()){
                toast("No se escaneo producto")
            }else{
                Log.e(TAG,"Producto ${scan.contents}")
            }
        }
    }
}