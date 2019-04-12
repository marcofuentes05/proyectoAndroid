package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.fuent.lispinterpreter.LISP.Interprete
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.classifier.CodeProcessor
import io.github.kbiakov.codeview.highlight.ColorTheme

class Editor : AppCompatActivity() {

    var code_id : String = ""
    var string : String  = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        // train classifier on app start
        CodeProcessor.init(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        code_id =intent.getStringExtra(Carpetas.EXTRA_CARPETA_ID)

        var codeView : CodeView = findViewById(R.id.code_view)

        val instance = FirebaseFirestore.getInstance()
        var codigo_lisp = ""
        var nombre : TextView = findViewById(R.id.nameLisp)
        instance.collection("archivos").document(code_id).get().addOnSuccessListener { res->
            codigo_lisp = res["script"].toString()
            println("El codigo almacenado en firestore es: $codigo_lisp")

            var t = "\\n".trimIndent()

            string = codigo_lisp.replace(t,"\n")

            codeView.setOptions(Options.get(this)
                    .withLanguage("lisp")
                    .withCode(string)
                    .withTheme(ColorTheme.MONOKAI))

        }.addOnFailureListener{
            Toast.makeText(this, "Hubieron errores y no fue posible obtener el codigo", Toast.LENGTH_LONG).show()
        }
    }

    fun back(view: View){
        finish()
    }

    fun execute (view:View){
        Toast.makeText(this, "Funcionalidad pendiente...", Toast.LENGTH_LONG).show()
        finish()
        /**string = string.replace("[","")
        string = string.replace("]","")
        var string0 = string.replace("\n","")
        println(string0)
        var interprete = Interprete(string0)
        /*var resultado = */interprete.interpretar()
        //Toast.makeText(this, "Output: $resultado", Toast.LENGTH_LONG).show()*/
    }

    override fun onBackPressed(){
        finish()
    }
}