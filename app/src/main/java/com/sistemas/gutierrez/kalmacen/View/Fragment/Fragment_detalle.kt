package com.sistemas.gutierrez.kalmacen.View.Fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.sistemas.gutierrez.kalmacen.Controller.Observable
import com.sistemas.gutierrez.kalmacen.Controller.Observador
import com.sistemas.gutierrez.kalmacen.Model.Constant
import com.sistemas.gutierrez.kalmacen.Model.inTransaction
import com.sistemas.gutierrez.kalmacen.R
import kotlinx.android.synthetic.main.fragment_detalle.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class Fragment_detalle : Fragment(), Observador {

    private lateinit var gv: View
    private var observable: Observable? = null
    private val TAG by lazy { Fragment_detalle::class.java.simpleName }

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
        gv = inflater.inflate(R.layout.fragment_detalle, container, false)
        /*gv.recycler_carga.layoutManager = GridLayoutManager(activity,2)
        gv.recycler_carga.hasFixedSize()
        gv.recycler_carga.adapter = Carga_adapter(activity!!,1)*/
        gv.img_retroceder.onClick {
            //activity!!.tool_bar.visible()
            (activity as AppCompatActivity).supportFragmentManager.inTransaction {
                remove(this@Fragment_detalle)
            }
        }
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