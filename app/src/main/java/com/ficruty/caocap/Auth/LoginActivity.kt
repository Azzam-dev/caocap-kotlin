package com.ficruty.caocap.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.Explore
import com.ficruty.caocap.PersonalActivity
import com.ficruty.caocap.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

     var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

         if ( auth.currentUser!= null){
            val  intent  = Intent(this,PersonalActivity::class.java)
             startActivity(intent)
         }

        login_sign_in_button.setOnClickListener{
            val email=login_email_edit_text.text.toString()
            val password=login_password_edit_text.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill your data",Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                    val intent= Intent(this, Explore::class.java)
                    intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this,"${it.message}",Toast.LENGTH_SHORT).show()
                }
            }

        }

        login_reset_password_button.setOnClickListener {
            startActivity(Intent(this,ResetPasswordActivity::class.java))
        }

        login_sign_up_button.setOnClickListener{
            startActivity(Intent(this,RegisterationActivity::class.java))
        }

    }
}
