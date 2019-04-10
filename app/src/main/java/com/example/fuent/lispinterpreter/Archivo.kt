package com.example.fuent.lispinterpreter

class Archivo {
    var nombre: String= ""
    var autor : String = ""
    var script : String = ""

    constructor(n: String, a: String, s:String){
        nombre = n
        autor = a
        script = s
    }

    fun toMap():HashMap<String,Any>{
        val res = HashMap<String, Any>()
        res["nombre"] = nombre
        res["autor"]= autor
        res["script"]= script
        return res
    }
}