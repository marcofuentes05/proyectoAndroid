package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var usuario: EditText
    private lateinit var contrasena: EditText
    private lateinit var forgot : TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        usuario=findViewById(R.id.usuario)
        contrasena=findViewById(R.id.Logo)
        progressBar=findViewById(R.id.progressBar2)
        auth= FirebaseAuth.getInstance()
        forgot = findViewById(R.id.olvidoContra)

    }
    fun olvidoPassword(view: View) {
        startActivity(Intent(this,OlvidoContraActivity::class.java))
        finish()
    }
    fun crear(view: View){
        startActivity(Intent(this, NuevoUsuario::class.java))
        finish()
    }
    fun login(view: View){
        loginUser()
    }
    private fun loginUser(){
        val user:String=usuario.text.toString()
        val password:String=contrasena.text.toString()

        if(!TextUtils.isEmpty(user) &&!TextUtils.isEmpty(password)){
            progressBar.visibility=View.VISIBLE
            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    (this.application as MyApplication).setUser(user)
                    action()
                    finish()
                }else{
                    Toast.makeText(this,"No se pudo iniciar sesion...", Toast.LENGTH_LONG).show()
                    progressBar.visibility=View.INVISIBLE
                }
            }
        }

    }

    private fun action(){
        startActivity(Intent(this,Carpetas::class.java))
    }
}
