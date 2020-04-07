package com.sistemas.gutierrez.kalmacen.View.Fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sistemas.gutierrez.kalmacen.Controller.Carga_adapter
import com.sistemas.gutierrez.kalmacen.Controller.Observable
import com.sistemas.gutierrez.kalmacen.Controller.Observador
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.R
import kotlinx.android.synthetic.main.fragment_carga.view.*

class Fragment_carga_general : Fragment(), Observador {

    private lateinit var gv: View
    private var observable: Observable? = null
    private val TAG by lazy { Fragment_carga_general::class.java.simpleName }

    override fun onAttach(context: Context) {
        super.onAttach(context)
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
        Constant.respuesta_keyevent = 0
        gv = inflater.inflate(R.layout.fragment_carga, container, false)
        gv.recycler_carga.layoutManager = LinearLayoutManager(activity)
        gv.recycler_carga.hasFixedSize()
        gv.recycler_carga.adapter = Carga_adapter(activity!!,0)
        observable?.agregarObservador(this)
        return gv
    }

    override fun onResume() {
        super.onResume()
    }

    override fun actualizar() {
        //Completar
    }
}