package com.ficruty.caocap.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.Controller.MainActivity
import com.ficruty.caocap.HomeActivity
import com.ficruty.caocap.Models.Users
import com.ficruty.caocap.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registeration.*

class RegisterationActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        auth= Firebase.auth

        registration_sign_up_button.setOnClickListener(){
            var username=registration_username_edit_text.text.toString()
            var email=registration_email_edit_text.text.toString()
            var password=registration_password_edit_text.text.toString()
            var provider="Firebase"


            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill your data",Toast.LENGTH_SHORT).show()
            }else{
                if(username.length<4){
                    Toast.makeText(this,"The username must be 4 characters or more.",Toast.LENGTH_SHORT).show()
                }else{
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                        var uid=Firebase.auth.uid.toString()
                        var reference=Firebase.database.getReference("users/$uid")
                        reference.setValue(Users(username,email,provider))
                        var intent= Intent(this,HomeActivity::class.java)
                        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }.addOnFailureListener(){
                        Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                    }
                }

            }



        }

        registration_sign_in_button.setOnClickListener(){
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }
}
