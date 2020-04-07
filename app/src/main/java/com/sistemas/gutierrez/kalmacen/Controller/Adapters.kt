package com.sistemas.gutierrez.kalmacen.Controller

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.sistemas.gutierrez.kalmacen.Model.*
import com.sistemas.gutierrez.kalmacen.R
import com.sistemas.gutierrez.kalmacen.View.Dialog.Dialog_camion
import com.sistemas.gutierrez.kalmacen.View.Dialog.Dialog_conteo
import com.sistemas.gutierrez.kalmacen.View.Fragment.Fragment_detalle
import kotlinx.android.synthetic.main.activity_navegacion.*
import kotlinx.android.synthetic.main.row_camion.view.*
import kotlinx.android.synthetic.main.row_carga.view.*
import kotlinx.android.synthetic.main.row_detalle.view.*
import kotlinx.android.synthetic.main.row_inventario.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk27.coroutines.onClick


class Inventario_adapter(private val activity: Activity): RecyclerView.Adapter<Inventario_adapter.ViewHolder>(){

    private val array = arrayListOf<Inventario>()
    private val TAG by lazy { Inventario_adapter::class.java.simpleName }

    init {
        val inv = Inventario("1049","GLORIA EVAPORADA SUPERLIGHT 24UNI X 400GR",64,24,3,54,10,1,20,2)
        for (i in 0..5){
            array.add(inv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_inventario,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(array[position],activity)
    }

    override fun getItemCount() = array.size

    override fun getItemViewType(position: Int) = position

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Inventario,activity: Activity){
            itemView.txt_codigo.text = item.codigo
            itemView.txt_descripcion.text = item.descripcion
            itemView.txt_ps.text = item.ps.toString()
            itemView.txt_bs.text = item.bs.toString()
            itemView.txt_us.text = item.us.toString()
            if (item.pallet == 0 && item.bulto == 0 && item.unidad == 0){
                itemView.ln_ingresado.desaparece()
                itemView.ln_diferencia.desaparece()
            }else {
                itemView.ln_ingresado.visible()
                itemView.ln_diferencia.visible()
                itemView.txt_pallet.text = item.pallet.toString()
                itemView.txt_bulto.text = item.bulto.toString()
                itemView.txt_unidad.text = item.unidad.toString()
                itemView.txt_pdif.text = "1"
                itemView.txt_bdif.text = "1"
                itemView.txt_udif.text = "1"
            }
            itemView.linear_inventario.onClick {
                Dialog_conteo().show((activity as AppCompatActivity).supportFragmentManager,"Dialog")
            }
            itemView.img_posicion.onClick {
                when(itemView.linear_ubicacion.isVisible) {
                    false -> {
                        itemView.linear_ubicacion.visible()
                        itemView.flex_ubicacion.removeAllViews()
                        val array = arrayListOf("B-3-A-5","B-3-B-5","B-3-C-5","B-3-D-5","B-3-A-6","B-3-B-6","B-3-C-6","B-3-D-6","B-3-A-7","B-3-B-7","B-3-C-7","B-3-D-7","B-3-A-8","B-3-B-8","B-3-C-8","B-3-D-8")
                        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                        params.setMargins(3, 3, 3, 3)
                        for (i in 0 until array.size) {
                            val place = TextView(activity)
                            place.padding = 5
                            place.layoutParams = params
                            place.typeface = Typeface.DEFAULT_BOLD
                            place.textSize = 14f
                            place.backgroundResource = R.drawable.borde
                            place.text = array[i]
                            place.textColor = Color.parseColor("#5A5A5A")
                            itemView.flex_ubicacion.addView(place)
                        }
                    }
                    true -> itemView.linear_ubicacion.desaparece()
                }
            }
        }
    }
}

class Carga_adapter(private val activity: Activity,private val opt: Int): RecyclerView.Adapter<Carga_adapter.ViewHolder>(), Vista{

    private val camiones = arrayListOf<Camiones>()
    private val carga = arrayListOf<Carga>()
    private val TAG by lazy { Carga_adapter::class.java.simpleName }

    init {
        val car = Carga("1049","GLORIA EVAPORADA SUPERLIGHT 24UNI X 400GR","3","54","10")
        val cam = Camiones("572","588 - YONY MALVACEDA QUINONES")
        for (i in 0..6){
            camiones.add(cam)
            carga.add(car)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Constant.adapvista = this
        val view = when(opt) {
            0 -> LayoutInflater.from(parent.context).inflate(R.layout.row_carga,parent,false)
            else -> LayoutInflater.from(parent.context).inflate(R.layout.row_camion,parent,false)
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(opt) {
            0 -> holder.bindcarga(carga[position],activity)
            else -> holder.bindcamion(camiones[position],activity)
        }
    }

    override fun getItemCount() = when(opt) {
        0 -> carga.size
        else -> camiones.size
    }

    override fun getItemViewType(position: Int) = position

    override fun notificarCambios() {
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bindcamion(item: Camiones,activity: Activity){
            itemView.txt_camion.text = item.codigo
            itemView.txt_camionero.text = item.nombre
            itemView.card_camion.onClick {
                Dialog_camion().show((activity as AppCompatActivity).supportFragmentManager,"Dialog")
            }
        }
        fun bindcarga(item: Carga,activity: Activity){
            itemView.flex_camiones.removeAllViews()
            val array = arrayListOf("588","574","563","589","724","542","386","500","520")
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.setMargins(3, 3, 3, 3)
            for (i in 0 until array.size) {
                val place = TextView(activity)
                place.padding = 5
                place.layoutParams = params
                place.typeface = Typeface.DEFAULT_BOLD
                place.textSize = 14f
                if (i % 2 == 0){
                    place.backgroundResource = R.drawable.borde
                    place.textColor = Color.parseColor("#5A5A5A")
                }else {
                    place.backgroundResource = R.drawable.camion_despachado
                    place.textColor = Color.WHITE
                }
                place.text = array[i]
                itemView.flex_camiones.addView(place)
            }
            itemView.card_carga.onClick {
                //activity.tool_bar.desaparece()
                activity.toast("Click")
                (activity as AppCompatActivity).supportFragmentManager.inTransaction {
                    add(R.id.frame_contenedor,Fragment_detalle())
                }
            }
        }
    }
}

class Detalle_adapter(private val activity: Activity): RecyclerView.Adapter<Detalle_adapter.ViewHolder>(){

    private val array = arrayListOf<Inventario>()
    private val TAG by lazy { Detalle_adapter::class.java.simpleName }

    init {
        val inv = Inventario("1049","GLORIA EVAPORADA SUPERLIGHT 24UNI X 400GR",64,24,3,54,10,1,20,2)
        for (i in 0..5){
            array.add(inv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Constant.adapdetalle = this
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_detalle,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(array[position],activity)
    }

    /*override fun accionDeslizar(opt: Int, posicion: Int) {
        if (opt != 4) {
            when(opt) {
                0 -> array.removeAt(posicion)
                1 -> {
                    Constant.detalle = 0
                    Constant.posicion = posicion
                    Constant.item = array[posicion]
                    array.removeAt(posicion)
                    //Dialog_parcial().show((activity as AppCompatActivity).supportFragmentManager,"Dialog")
                }
                2 -> array.add(array.size - 1,array[posicion])
                3 -> array.add(posicion,Constant.item!!)
            }
            notifyItemRemoved(posicion)
            notifyItemRangeChanged(posicion,array.size)
        }
    }*/

    override fun getItemCount() = array.size

    override fun getItemViewType(position: Int) = position

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Inventario,activity: Activity){
            itemView.txt_cod_d.text = item.codigo
            itemView.txt_desc_d.text = item.descripcion
            itemView.txt_pallet_d.text = item.ps.toString()
            itemView.txt_bulto_d.text = item.bs.toString()
            itemView.txt_unidad_d.text = item.us.toString()
            itemView.card_detalle.onClick {
                //Dialog_parcial().show((activity as AppCompatActivity).supportFragmentManager,"Dialog")
            }
        }
    }
}

/*class Carga_adapter(private val activity: Activity): RecyclerView.Adapter<Inventario_adapter.ViewHolder>(){

    private val array = arrayListOf<Inventario>()
    private val TAG by lazy {Inventario_adapter::class.java.simpleName}

    init {
        val inv = Inventario("1049","GLORIA EVAPORADA SUPERLIGHT 24UNI X 400GR",64,24,3,54,10,1,20,2)
        for (i in 0..5){
            array.add(inv)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_inventario,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(array[position],activity)
    }

    override fun getItemCount() = array.size

    override fun getItemViewType(position: Int) = position

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(item: Inventario,activity: Activity){
            itemView.txt_codigo.text = item.codigo
            itemView.txt_descripcion.text = item.descripcion
            itemView.txt_ps.text = item.ps.toString()
            itemView.txt_bs.text = item.bs.toString()
            itemView.txt_us.text = item.us.toString()
            if (item.pallet == 0 && item.bulto == 0 && item.unidad == 0){
                itemView.ln_ingresado.desaparece()
                itemView.ln_diferencia.desaparece()
            }else {
                itemView.ln_ingresado.visible()
                itemView.ln_diferencia.visible()
                itemView.txt_pallet.text = item.pallet.toString()
                itemView.txt_bulto.text = item.bulto.toString()
                itemView.txt_unidad.text = item.unidad.toString()
                itemView.txt_pdif.text = "1"
                itemView.txt_bdif.text = "1"
                itemView.txt_udif.text = "1"
            }
            itemView.linear_inventario.onClick {
                Dialog_parcial().show((activity as AppCompatActivity).supportFragmentManager,"Dialog")
            }
        }
    }
}*/