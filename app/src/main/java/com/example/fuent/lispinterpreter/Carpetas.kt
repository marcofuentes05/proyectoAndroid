package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.fuent.lispinterpreter.Adapters.RecyclerViewAdaptadorCarpeta
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Carpetas : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    lateinit var dbCollection : FirebaseFirestore
    lateinit var auth : FirebaseAuth

    lateinit var recyclerView : RecyclerView
    lateinit var recyclerViewAdapter : RecyclerViewAdaptadorCarpeta

    lateinit var listaCarpetas : ArrayList<Carpeta>

    companion object {
        const val EXTRA_CARPETA_ID = ""
        const val EXTRA_ARCHIVO_ID = ""

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carpetas)

        dbCollection = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerCarpetas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        listaCarpetas = obtenerCarpetas()
    }

    fun obtenerCarpetas():ArrayList<Carpeta>{
        val db = FirebaseFirestore.getInstance()
        var lista: ArrayList<Carpeta> = arrayListOf()
        db.collection("usuarios").whereEqualTo("correo",(this.application as MyApplication).getUser())
                .get()
                .addOnSuccessListener {result ->
                    var db0 = FirebaseFirestore.getInstance()
                    var p0 = result.documents[0]["carpetas"].toString()
                    var p1 = p0.substring(1,p0.length-1)
                    //Ya tengo una lista con las direcciones de cada carpeta
                    var p = p1.split(", ")
                    for (alfa in p) {
                        db0.collection("carpetas").document(alfa).get().addOnSuccessListener { res ->
                            var list: ArrayList<String> = arrayListOf()
                            var files = res["archivos"].toString()
                            var files0 = files.substring(1, files.length - 1)
                            var files1 = files0.split(", ")
                            list = listToArrayList(files1)
                            var t = Carpeta(res["nombre"].toString(), list, res["autor"].toString(),res.id)
                            lista.add(t)

                            recyclerViewAdapter = RecyclerViewAdaptadorCarpeta(lista)
                            recyclerView.adapter = recyclerViewAdapter
                            //Toast.makeText(this, "SI se pudo bro :D ", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        return lista
    }

    fun nuevaCarpeta(view:View){
        //Solo nos lleva a la nueva activity
        var intent = Intent(this,NuevaCarpeta :: class.java)
        startActivity(intent)
        finish()
    }

    fun listToArrayList(lista : List<String>):ArrayList<String>{
        var res : ArrayList<String> = arrayListOf()
        for (i in lista){
            res.add(i)
        }
        return res
    }
}