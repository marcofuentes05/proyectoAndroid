package com.example.fuent.lispinterpreter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.fuent.lispinterpreter.Carpetas.Companion.EXTRA_CARPETA_ID
import com.google.firebase.firestore.FirebaseFirestore

class compartirCarpeta : AppCompatActivity() {

    private lateinit var correo : EditText
    private var id_carpeta : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compartir_carpeta)
        id_carpeta = intent.getStringExtra(EXTRA_CARPETA_ID)
        correo = findViewById(R.id.correo)

    }
    fun share (view: View){
        if (correo.text.toString() != ""){
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").whereEqualTo("correo",correo.text.toString()).get().addOnSuccessListener {result ->
                if (result.documents.size != 0){
                    var userID = result.documents[0].id.toString()
                    println(userID)
                    var lst = result.documents[0]["carpetas"].toString()
                    var lst0 = lst.substring(1,lst.length-1)
                    var list = lst.split(", ")

                    var lista = listToArrayList(list)

                    if (lista[0]!="ul"){
                        lista.add(id_carpeta)
                        db.collection("usuarios").document(userID).update("carpetas",lista as List<String>)
                        Toast.makeText(this,"Carpeta compartida con Exito", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"No se pudo tbh :(", Toast.LENGTH_LONG).show()
                        list = arrayListOf()
                    }
                    finish()
                }else{

                }
                Toast.makeText(this,"El correo que ingresaste no existe...", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"Debes ingresar un correo electrónico", Toast.LENGTH_LONG).show()
        }
    }
    fun listToArrayList(lista : List<String>):ArrayList<String>{
        var res : ArrayList<String> = arrayListOf()
        for (i in lista){
            res.add(i)
        }
        return res
    }

    override fun onBackPressed() {
        finish()
    }
}
