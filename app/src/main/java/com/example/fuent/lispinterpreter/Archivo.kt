package com.example.fuent.lispinterpreter

class Archivo {
    var nombre: String= ""
    var autor : String = ""
    var script : String = ""
    var script_id : String = ""
    constructor(n: String, a: String, s:String, i:String){
        nombre = n
        autor = a
        script = s
        script_id = i
    }

    fun toMap():HashMap<String,Any>{
        val res = HashMap<String, Any>()
        res["nombre"] = nombre
        res["autor"]= autor
        res["script"]= script
        return res
    }
}