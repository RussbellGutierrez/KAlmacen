package com.sistemas.gutierrez.kalmacen.Model

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.text.TextUtils
import android.util.Log
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class SQLHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DATABASE, null, VERSION) {

    private val context = ctx
    private val TAG = SQLHelper::class.java.simpleName

    companion object {
        const val DATABASE = "KAlmacen"
        const val VERSION = 1
        @SuppressLint("StaticFieldLeak")
        private var instance: SQLHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): SQLHelper {
            if (instance == null) {
                instance = SQLHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG,"Creando base de datos")
        val creacion = String.format("basedatos_upgrade.sql")
        leerEjecutarScriptSQL(db,context,creacion)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.w(TAG,"Actualizando base de datos de $oldVersion a $newVersion")
        val actualizacion = String.format("basedatos_upgrade.sql")
        leerEjecutarScriptSQL(db,context,actualizacion)
    }

    private fun leerEjecutarScriptSQL(db: SQLiteDatabase, ctx: Context, archivo: String){
        if (TextUtils.isEmpty(archivo)){
            Log.d(TAG,"El archivo de ScriptSQL está vacio")
            return
        }
        Log.d(TAG,"Script encontrado. Ejecutando...")
        val assetManager = ctx.assets
        var reader: BufferedReader? = null
        try {
            val am = assetManager.open(archivo)
            val isr = InputStreamReader(am)
            reader = BufferedReader(isr)
            ejecutarScript(db,reader)
        }catch (e: IOException){
            Log.e(TAG,"IOException:",e)
        }finally {
            if (reader != null){
                try {
                    reader.close()
                }catch (e: IOException){
                    Log.e(TAG,"IOException:",e)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun ejecutarScript(db: SQLiteDatabase, reader: BufferedReader){
        var line = reader.readLine()
        var statement = StringBuilder()
        if(line != null){
            do{
                statement.append(line)
                statement.append("\n")
                if (line.endsWith(";")){
                    db.execSQL(statement.toString())
                    statement = StringBuilder()
                }
                line = reader.readLine()
            }while (line != null)
        }
    }
}

// Access property for Context
val Context.database: SQLHelper
    get() = SQLHelper.getInstance(applicationContext)