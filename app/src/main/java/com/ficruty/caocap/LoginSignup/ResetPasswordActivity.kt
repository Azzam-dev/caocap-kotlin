package com.ficruty.caocap.LoginSignup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        reset_password_activity_reset_button.setOnClickListener(){
            Firebase.auth.sendPasswordResetEmail(reset_password_activity_email_edit_text.text.toString()).addOnSuccessListener {
                Toast.makeText(this,"The reset password is sent to your email", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,LoginActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(), Toast.LENGTH_LONG).show()
            }
        }

        reset_password_activity_signup_button.setOnClickListener(){
            startActivity(Intent(this,RegisterationActivity::class.java))
        }

    }
}