package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_email.*

class ChangeEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)




        change_email_acitivty_update_button.setOnClickListener(){
            if(Firebase.auth.currentUser!!.email.toString()==change_email_acitivty_old_email_edit_text.text.toString()){
                Firebase.auth.currentUser!!.updateEmail(change_email_acitivty_new_email_edit_text.text.toString()).addOnSuccessListener {
                    Toast.makeText(this,"The email was updated. we send message to verify to your email",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                }
                }else{
                Toast.makeText(this,"The old email is not correct.",Toast.LENGTH_SHORT).show()
            }
        }
    }
}