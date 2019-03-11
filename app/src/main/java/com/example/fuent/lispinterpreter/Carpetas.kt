package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView

class Carpetas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carpetas)

        var atras : Button = findViewById(R.id.atrasButton)
        var lista : ListView = findViewById(R.id.lista)
        var nuevaCarpeta : ImageView = findViewById(R.id.nuevaCarpeta)

    }
    fun atrasButtonClick(view: View){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
    }



}
