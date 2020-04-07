package com.sistemas.gutierrez.kalmacen.View

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.sistemas.gutierrez.kalmacen.Model.goTo
import com.sistemas.gutierrez.kalmacen.R
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class Activity_login: AppCompatActivity() {

    private var imagenes = intArrayOf(R.drawable.primero,R.drawable.segundo,R.drawable.tercero)
    private val TAG by lazy { Activity_login::class.java.simpleName }

    private val imglistener: ImageListener = ImageListener { position, imageView ->
        imageView.setImageResource(imagenes[position])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        carousel.pageCount = imagenes.size
        carousel.setImageListener(imglistener)
        btn_login.onClick { this@Activity_login.goTo<Activity_contenedor>() }
    }
}