package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_email.*

class ChangeEmailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_email)




        change_email_acitivty_update_button.setOnClickListener(){
            var currentEmail=change_email_acitivty_current_email_edit_text.text.toString()
            var password=change_email_acitivty_password_edit_text.text.toString()
            var newEmail=change_email_acitivty_new_email_edit_text.text.toString().trim().toLowerCase()
            var confirmEmail=change_email_acitivty_confirm_new_email_edit_text.text.toString().trim()

            Firebase.auth.signInWithEmailAndPassword(currentEmail,password).addOnSuccessListener {
                if(newEmail.equals(confirmEmail,ignoreCase = true)){
                    Firebase.auth.currentUser!!.updateEmail(newEmail).addOnSuccessListener {
                        Firebase.database.getReference("users").child(Firebase.auth.uid.toString()).child("email").setValue(newEmail).addOnSuccessListener {
                            Firebase.auth.currentUser!!.sendEmailVerification().addOnSuccessListener {
                                Toast.makeText(this,"The email was updated. we send message to verify to your email",Toast.LENGTH_SHORT).show()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                            }
                        }
                    }.addOnFailureListener {
                        Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this,"The email and confirm is not matched. ",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
            }
            }
        }
    }