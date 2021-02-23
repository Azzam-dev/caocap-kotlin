package com.ficruty.caocap.LoginSignup

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ficruty.caocap.Controller.MainActivity
import com.ficruty.caocap.Explore
import com.ficruty.caocap.HomeActivity
import com.ficruty.caocap.Models.Users
import com.ficruty.caocap.R
import com.ficruty.caocap.Services.Language
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_registeration.*

class RegisterationActivity : AppCompatActivity(){

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registeration)

        auth= Firebase.auth
        var language: Language = Language.AR;

//        @android:color/secondary_text_material_light
//        @android:color/secondary_text_light



        registration_language_switch.setOnCheckedChangeListener(){ buttonView, isChecked ->

        if(isChecked){
            registration_language_Arabic_text_view.setTextColor(ContextCompat.getColor(this,R.color.black))
            registration_language_English_text_view.setTextColor(ContextCompat.getColor(this,R.color.grey))
            language= Language.AR;
        }else{
            registration_language_Arabic_text_view.setTextColor(ContextCompat.getColor(this,R.color.grey))
            registration_language_English_text_view.setTextColor(ContextCompat.getColor(this,R.color.black))
            language= Language.EN;
        }

        }

        registration_sign_up_button.setOnClickListener(){
            val username=registration_username_edit_text.text.toString()
            val email=registration_email_edit_text.text.toString()
            val password=registration_password_edit_text.text.toString()

            if(username.isEmpty() || email.isEmpty() || password.isEmpty()){
                Toast.makeText(this,"Please fill your data",Toast.LENGTH_SHORT).show()
            }else{
                if(username.length<4){
                    Toast.makeText(this,"The username must be 4 characters or more.",Toast.LENGTH_SHORT).show()
                }else{
                    auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                        val uid=Firebase.auth.uid.toString()
                        val reference=Firebase.database.getReference("users/$uid")
                        val provider=Firebase.auth.currentUser?.providerId.toString()
                        reference.setValue(Users(username,email,provider)).addOnSuccessListener {
                            Firebase.auth.setLanguageCode(language.lan);
                            val intent= Intent(this,Explore::class.java)
                            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }.addOnFailureListener(){
                            Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                        }

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

