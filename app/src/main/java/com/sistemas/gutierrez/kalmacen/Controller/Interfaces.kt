package com.sistemas.gutierrez.kalmacen.Controller

interface Observable {
    fun agregarObservador(o: Observador)
    fun removerObservador(o: Observador)
    fun notificarObservador()
}

interface Observador {
    fun actualizar()
}

interface Detalle {
    fun accionDeslizar(opt: Int, posicion: Int)
}

interface Vista {
    fun notificarCambios()
}