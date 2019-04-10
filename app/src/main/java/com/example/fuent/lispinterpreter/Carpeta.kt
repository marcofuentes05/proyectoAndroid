package com.example.fuent.lispinterpreter

class Carpeta {
    var id : String = ""
    var nombre : String = ""
    var codigos: ArrayList<String> = arrayListOf()
    var autor : String = ""

    constructor(n : String, c:ArrayList<String>,a:String, i:String){
        codigos = c
        nombre = n
        autor = a
        id = i
    }

    fun addCodigo (codigo : String) {
        codigos.add(codigo)
    }

    fun toMap(): HashMap<String,Any>{
        val res = HashMap<String, Any>()
        res["nombre"] = nombre
        res["codigos"]=ArrayList<String>()
        res["autor"]=autor
        return res
    }

    override fun toString(): String {
        return """
            Nombre: $nombre \n Autor: $autor \n Codigos: $codigos
        """.trimIndent()
    }
}