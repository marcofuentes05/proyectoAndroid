package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.classifier.CodeProcessor
import io.github.kbiakov.codeview.highlight.ColorTheme

class Editor : AppCompatActivity() {

    var code_id : String = ""

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

            var t = "\\n".trimIndent()

            var string = codigo_lisp.replace(t,"\n")

            println(string)
            codeView.setOptions(Options.get(this)
                    .withLanguage("lisp")
                    .withCode(string)
                    .withTheme(ColorTheme.MONOKAI))
            Toast.makeText(this, "Codigo recibido", Toast.LENGTH_LONG).show()
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
    }

    override fun onBackPressed(){
        finish()
    }
}