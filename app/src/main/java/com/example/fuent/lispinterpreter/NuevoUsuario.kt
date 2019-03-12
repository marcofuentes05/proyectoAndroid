package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class NuevoUsuario : AppCompatActivity() {
    private lateinit   var textView: TextView
    private lateinit var name: EditText
    private lateinit var user: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var create: Button






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_usuario)
        name=findViewById(R.id.name)
        user=findViewById(R.id.user)
        email=findViewById(R.id.email)
        password=findViewById(R.id.password)

        progressBar= findViewById(R.id.progressBar)

        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()

        dbReference=database.reference.child("User")

    }

    fun register(view: View){

        createNewAccount()
    }

    private fun createNewAccount(){
        val name:String=name.text.toString()
        val user:String=user.text.toString()
        val email:String=email.text.toString()
        val password:String=password.text.toString()

        if(!TextUtils.isEmpty(name) &&!TextUtils.isEmpty(user) &&!TextUtils.isEmpty(email) &&!TextUtils.isEmpty(password))

            progressBar.visibility=View.VISIBLE
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){
                    task ->
                    if(task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)
                        try {
                            val userBD = dbReference.child(user?.uid.toString())
                            userBD.child("Name").setValue(name)
                            userBD.child("User").setValue(user)
                            action()
                            Toast.makeText(this,"Correo enviado",Toast.LENGTH_LONG).show()
                        }catch(e:Exception){

                        }
                    }
                }

    }
    private fun action(){
        startActivity(Intent(this,Login::class.java))
    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
                ?.addOnCompleteListener(this){
                    task ->
                    if(task.isComplete){
                        Toast.makeText(this,"Correo enviado",Toast.LENGTH_LONG).show()
                    }else{Toast.makeText(this,"Error al enviar correo",Toast.LENGTH_LONG).show()

                    }
                }
    }
}
