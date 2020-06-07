package com.ficruty.caocap.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.HomeActivity
import com.ficruty.caocap.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         auth= Firebase.auth

        login_sign_in_button.setOnClickListener(){
            val email=login_email_edit_text.text.toString()
            val password=login_password_edit_text.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill your data",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    var intent= Intent(this, HomeActivity::class.java)
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }.addOnFailureListener(){
                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                }
            }

        }

        login_sign_up_button.setOnClickListener(){
            startActivity(Intent(this,RegisterationActivity::class.java))
        }

    }
}
