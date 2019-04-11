package com.example.fuent.lispinterpreter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class OlvidoContraActivity : AppCompatActivity() {

    private lateinit var txtCorreo: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_olvido_contra)

        txtCorreo = findViewById(R.id.txtCorreo)
        auth = FirebaseAuth.getInstance()

        progressBar = findViewById(R.id.progressBar21)
    }

    override fun onBackPressed() {
        var intent = Intent(this, Login :: class.java)
        startActivity(intent)
        finish()
    }

    fun send(view: View){
        val correo1 = txtCorreo.text.toString()

        if (!TextUtils.isEmpty(correo1)) {
            auth.sendPasswordResetEmail(correo1)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            progressBar.visibility = View.VISIBLE
                            startActivity(Intent(this, Login::class.java))
                        } else {
                            Toast.makeText(this, "Error al enviar el correo", Toast.LENGTH_LONG).show()
                        }
                    }
        }
    }
}
