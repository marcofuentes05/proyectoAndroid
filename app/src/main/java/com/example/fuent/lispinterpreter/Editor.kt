package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.fuent.lispinterpreter.lispI.Atomo
import com.example.fuent.lispinterpreter.lispI.Errores.*
import com.example.fuent.lispinterpreter.lispI.LispInterpreter
import com.google.firebase.firestore.FirebaseFirestore
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.classifier.CodeProcessor
import io.github.kbiakov.codeview.highlight.ColorTheme
import java.io.BufferedReader
import java.io.FileReader
import java.lang.Exception
import java.util.ArrayList
import java.util.HashMap

class Editor : AppCompatActivity() {

    fun back(view:View){
        finish()
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // train classifier on app start
        CodeProcessor.init(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)


        var codeView: CodeView = findViewById(R.id.code_view)

        //Para cargar archivo de FB
        var code_id =intent.getStringExtra(Carpetas.EXTRA_ARCHIVO_ID)
        val instance = FirebaseFirestore.getInstance()
        var codigo_lisp = ""
        var nombre : TextView = findViewById(R.id.nameLisp)
        instance.collection("archivos").document(code_id).get().addOnSuccessListener { res->
        codigo_lisp = res["script"].toString()
            println("El codigo almacenado en firestore es: $codigo_lisp")
            var t ="\\n".trimIndent()
            var temp = codigo_lisp.replace(t,"\n")
            codeView.setOptions(Options.Default.get(this)
                    .withLanguage("lisp")
                    //.withCode(codigo_lisp)
                    .withCode(temp)
                    .withTheme(ColorTheme.MONOKAI))
        }.addOnFailureListener{
            Toast.makeText(this, "Hubieron errores y no fue posible obtener el codigo", Toast.LENGTH_LONG).show()
        }
        //var cod = codigo_lisp
        var interpretado = ""
        val interpreter = LispInterpreter()

        val ejBtn = findViewById<Button>(R.id.ejecutar)

        ejBtn.setOnClickListener {
            val se = codigo_lisp.replace("\\n", "sep")
            val parts = se.split("sep")
            //Toast.makeText(this, parts.toString(), Toast.LENGTH_LONG).show()

            var n = 0
            for (i in parts) {
                try {


                    val atomoAEvaluar = interpreter.parsearExpresion(parts[n], false, false)
                    if(atomoAEvaluar.toString() != "NIL") {
                        interpretado += interpreter.evaluar(atomoAEvaluar).toString() + "\n"
                    }
                    Toast.makeText(this, interpretado, Toast.LENGTH_LONG).show()
                    n += 1
                } catch (e: Exception){ }
            }
        }


    }
}

