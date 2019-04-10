package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.*
import com.example.fuent.lispinterpreter.Adapters.RecyclerViewAdaptadorArchivo
import com.example.fuent.lispinterpreter.Adapters.RecyclerViewAdaptadorCarpeta
import com.example.fuent.lispinterpreter.Carpetas.Companion.EXTRA_CARPETA_ID
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Archivos : AppCompatActivity() {

    lateinit var db : FirebaseFirestore
    lateinit var dbCollection : FirebaseFirestore
    lateinit var auth : FirebaseAuth

    lateinit var recyclerView : RecyclerView
    lateinit var recyclerViewAdapter : RecyclerViewAdaptadorArchivo

    lateinit var listaArchivos : ArrayList<Archivo>

    var id_carpeta : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archivos)
        id_carpeta =intent.getStringExtra(EXTRA_CARPETA_ID)

        recyclerView = findViewById(R.id.recyclerArchivos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        listaArchivos = obtenerArchivos()

        var nombre : TextView = findViewById(R.id.nombreCarpeta)
        nombre.text = id_carpeta
    }

    private fun obtenerArchivos(): ArrayList<Archivo> {
        val db = FirebaseFirestore.getInstance()
        var lista: ArrayList<Archivo> = arrayListOf()

        db.collection("carpetas").document(id_carpeta).get()
                .addOnSuccessListener {result->
                    var lst : ArrayList<String> = arrayListOf()

                    var res = result["codigos"].toString()
                    var res0 = res.substring(1,res.length-1)
                    var res1 = res0.split(", ")

                    lst = listToArrayList(res1)

                    val db0 = FirebaseFirestore.getInstance()
                    if (!lst.isEmpty()){
                        for (elemento in lst){
                            db0.collection("archivos").document(elemento).get().addOnSuccessListener {
                                res->
                                lista.add(Archivo(res["nombre"].toString(),res["autor"].toString(),res["script"].toString()))

                                recyclerViewAdapter = RecyclerViewAdaptadorArchivo(lista)
                                recyclerView.adapter = recyclerViewAdapter
                                Toast.makeText(this, "Mision Cumplida", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Carpeta vacía", Toast.LENGTH_SHORT).show()
                    }

                }.addOnFailureListener{
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

        return lista
    }

    fun back(view: View){
        val intent = Intent(this,Carpetas::class.java)
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
