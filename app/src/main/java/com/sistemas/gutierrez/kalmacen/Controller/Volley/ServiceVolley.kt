package com.sistemas.gutierrez.kalmacen.Controller.Volley

import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

/**
* Created by rgutierrez on 30/01/2018.
*/

class ServiceVolley{

    private val TAG: String = ServiceVolley::class.java.simpleName

    //TODO: Este método obtiene cualquier json sin importar su estructura
    //TODO: para castear o revisar los elementos, deben realizarse en metodos que reciban el dato correspondiente
    //TODO: No modificar esta sentencia

    fun post(path:String, params: JSONObject,bigcharge: Boolean = false, completionHandler: (response: Any?, message: String) -> Unit) {

        val jsonObjectReq = JsonObjectRequest(Method.POST,path,params,
                Response.Listener<JSONObject> { response ->
                    completionHandler(response,"Ok")
                },
                Response.ErrorListener { error ->
                    completionHandler(error,"Error")
                })
        if (bigcharge) BackendVolley.instance?.addTimeRequest(jsonObjectReq)
        BackendVolley.instance?.addToRequestQueue(jsonObjectReq, TAG)
    }
}