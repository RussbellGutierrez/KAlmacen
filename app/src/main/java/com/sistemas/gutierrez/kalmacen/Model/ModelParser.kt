package com.sistemas.gutierrez.kalmacen.Model

data class Inventario(var codigo: String,var descripcion: String,var pxb: Int,var bxu: Int,var ps: Int,var bs: Int,var us: Int,var pallet: Int,var bulto: Int,var unidad: Int)

data class Carga(var codigo: String,var descripcion: String,var pallet: String,var bulto: String,var unidad: String)

data class Camiones(var codigo: String,var nombre: String)