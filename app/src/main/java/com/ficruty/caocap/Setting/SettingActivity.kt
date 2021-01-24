package com.ficruty.caocap.Setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        if(Firebase.auth.currentUser!!.isEmailVerified==false){
            setting_acitivty_setting_text_view.text=null;
            setting_acitivty_setting_text_view.layoutParams.height=0;
            setting_acitivty_not_verify_message_text_view.text="${Firebase.auth.currentUser!!.email.toString()} is that your email? \nYou don't verity untill now ! ";
            setting_activity_send_verify_button.setOnClickListener(){
                Firebase.auth.currentUser!!.sendEmailVerification().addOnSuccessListener {
                    Toast.makeText(this,"The verfication sent.",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            setting_acitivty_not_verify_message_text_view.text=null;
            setting_activity_send_verify_button.text=null;
            constraintLayout8.layoutParams.height=0
        }

        setting_activity_change_email_button.setOnClickListener(){
            startActivity(Intent(this,ChangeEmailActivity::class.java))
        }

        setting_activity_change_password_button.setOnClickListener(){
            startActivity(Intent(this,ChangePasswordActivity::class.java))
        }
    }
}