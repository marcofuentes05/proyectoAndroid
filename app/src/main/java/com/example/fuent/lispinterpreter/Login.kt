package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var textView3: TextView
    private lateinit var textView: TextView
    private lateinit var usuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var login: Button
    private lateinit var nuevoUsuario: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        FirebaseApp.initializeApp(this)
        usuario=findViewById(R.id.usuario)
        contrasena=findViewById(R.id.contrasena)
        progressBar=findViewById(R.id.progressBar2)
        auth= FirebaseAuth.getInstance()
    }
    fun crear(view: View){
        startActivity(Intent(this, NuevoUsuario::class.java))

    }
    fun login(view: View){
        loginUser()
    }
    private fun loginUser(){
        val user:String=usuario.text.toString()
        val password:String=contrasena.text.toString()

        if(!TextUtils.isEmpty(user) &&!TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE

            auth.signInWithEmailAndPassword(user,password)
                    .addOnCompleteListener(this){
                        task ->
                        if(task.isSuccessful){
                            action()
                        }else{
                            Toast.makeText(this,"Error en l a autenticacion", Toast.LENGTH_LONG).show()

                        }
                    }
        }
    }

    private fun action(){
        startActivity(Intent(this,Carpetas::class.java))
    }
}
