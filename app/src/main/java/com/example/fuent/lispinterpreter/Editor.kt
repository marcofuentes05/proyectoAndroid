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
        var nombre : TextView = findViewById(R.id.nombreCarpeta)
        instance.collection("archivos").document(code_id).get().addOnSuccessListener { res->
            codigo_lisp = res["script"].toString()
        }

        var codigo_java = "package com.example.fuent.lispinterpreter\n" +
                "\n" +
                "import android.support.v7.app.AppCompatActivity\n" +
                "import android.os.Bundle\n" +
                "import io.github.kbiakov.codeview.CodeView\n" +
                "import io.github.kbiakov.codeview.adapters.Options\n" +
                "import io.github.kbiakov.codeview.classifier.CodeProcessor\n" +
                "import io.github.kbiakov.codeview.highlight.ColorTheme\n" +
                "\n" +
                "class Editor : AppCompatActivity() {\n" +
                "\n" +
                "    override fun onCreate(savedInstanceState: Bundle?) {\n" +
                "        // train classifier on app start\n" +
                "        CodeProcessor.init(this)\n" +
                "\n" +
                "        super.onCreate(savedInstanceState)\n" +
                "        setContentView(R.layout.activity_editor)\n" +
                "\n" +
                "        var codeView : CodeView = findViewById(R.id.code_view)\n" +
                "\n" +
                "        var codigo_java = \"\"\n" +
                "        codeView.setOptions(Options.Default.get(this)\n" +
                "                .withLanguage(\"java\")\n" +
                "                .withCode(\"(setq arbol '(1 . ((2 . 3) . (4 . 5))))\")\n" +
                "                .withTheme(ColorTheme.MONOKAI))\n" +
                "    }\n" +
                "}\n"
        codeView.setOptions(Options.Default.get(this)
                .withLanguage("lisp")
                .withCode(codigo_lisp)
                .withTheme(ColorTheme.MONOKAI))
    }

    fun back(view: View){
        var intent = Intent(this, Archivos::class.java)
        startActivity(intent)
        finish()
    }

    fun execute (view:View){
        Toast.makeText(this, "Funcionalidad pendiente...", Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onBackPressed(){
        var intent = Intent(this, Archivos::class.java)
        startActivity(intent)
        finish()
    }
}
