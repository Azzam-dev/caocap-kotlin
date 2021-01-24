package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)


        change_password_activity_update_button.setOnClickListener(){
            var email=change_password_activity_email_edit_text.text.toString()
            var currentPassword=change_password_activity_current_password_edit_text.text.toString()
            var newPassword=change_password_activity_new_password_edit_text.text.toString()
            var confirmPassword=change_password_activity_confirm_new_password_edit_text.text.toString()

            Firebase.auth.signInWithEmailAndPassword(email,currentPassword).addOnSuccessListener {
                if(newPassword.equals(confirmPassword,ignoreCase = false)){
                    Firebase.auth.currentUser!!.updatePassword(newPassword).addOnSuccessListener {
                        Toast.makeText(this,"The password was updated. ",Toast.LENGTH_SHORT).show()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"The password and confirm is not matched. ",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
            }
        }
    }
}