package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import io.github.kbiakov.codeview.CodeView
import io.github.kbiakov.codeview.adapters.Options
import io.github.kbiakov.codeview.classifier.CodeProcessor
import io.github.kbiakov.codeview.highlight.ColorTheme

class Editor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // train classifier on app start
        CodeProcessor.init(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)

        var codeView : CodeView = findViewById(R.id.code_view)

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
                .withLanguage("java")
                .withCode(codigo_java)
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
}
