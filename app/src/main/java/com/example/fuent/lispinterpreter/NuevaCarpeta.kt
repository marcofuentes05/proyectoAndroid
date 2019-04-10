package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.Task
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot


class NuevaCarpeta : AppCompatActivity() {

    private lateinit var guardarButton : Button
    private lateinit var atrasButton : Button
    private lateinit var nameText : EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var dbCollection : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_nueva_carpeta)

        nameText = findViewById(R.id.nameText)

        dbCollection = FirebaseFirestore.getInstance()

        auth= FirebaseAuth.getInstance()
    }

    fun save (view : View){
        if (nameText.text != null && nameText.text.toString() != ""){
            var nombre = nameText.text.toString()
            var list : ArrayList<String> = arrayListOf()
            var autor = ""
            var bd = FirebaseFirestore.getInstance()
            bd.collection("usuarios").whereEqualTo("correo",(this.application as MyApplication).getUser()).get()
                    .addOnSuccessListener { result->
                        autor = result.documents[0]["nombre"].toString()
                        var db0 = FirebaseFirestore.getInstance()
                        var carpetaT = Carpeta(nombre,list,autor,result.documents[0].id)
                        db0.collection("carpetas").add(carpetaT.toMap())
                                .addOnSuccessListener {generatedDoc ->
                                    var db1 = FirebaseFirestore.getInstance()
                                    db1.collection("usuarios").whereEqualTo("correo",(this.application as MyApplication).getUser())
                                            .get().addOnSuccessListener {result ->
                                                var p0 = result.documents[0]["carpetas"].toString()
                                                var p1 = p0.substring(1,p0.length-1)
                                                //Ya tengo una lista con las direcciones de cada carpeta
                                                var p = p1.split(", ")
                                                var newList = listToArrayList(p)
                                                var a = result.documents[0].id
                                                //Agrego el id del documento que acabo de generar a la lista de referencias
                                                val genID = generatedDoc.id
                                                newList.add(genID)
                                                db1.collection("usuarios").document(a).update("carpetas",newList as List<String>)
                                                back()
                                            }
                                }.addOnFailureListener{
                                    Toast.makeText(this,"No es posible realizar la operacion en este momento", Toast.LENGTH_LONG).show()
                                    back()
                                }
                    }.addOnFailureListener{
                        Toast.makeText(this,"No es posible realizar la operacion en este momento", Toast.LENGTH_LONG).show()
                        back()
                    }
        }else{
            Toast.makeText(this,"Tiene que haber un nombre especificado", Toast.LENGTH_LONG).show()
        }
    }

    fun back (view: View){
        val intent = Intent(this, Carpetas :: class.java)
        startActivity(intent)
        finish()
    }

    fun back (){
        val intent = Intent(this, Carpetas :: class.java)
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
