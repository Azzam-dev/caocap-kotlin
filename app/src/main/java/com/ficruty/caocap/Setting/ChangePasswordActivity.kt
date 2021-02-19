package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_password.*

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        var hidePassword=true

        change_password_acitivity_show_password_button.setOnClickListener {
            if(hidePassword){
                change_password_acitivity_show_password_button.setImageResource(R.drawable.icons8_eye_white_100px)
                change_password_activity_new_password_edit_text.transformationMethod= HideReturnsTransformationMethod.getInstance()
                change_password_activity_confirm_new_password_edit_text.setText("")
                change_password_activity_confirm_new_password_edit_text.isEnabled=false
                hidePassword=false
            }else{
                change_password_acitivity_show_password_button.setImageResource(R.drawable.icons8_eye_grey_100px_1)
                change_password_activity_new_password_edit_text.transformationMethod=PasswordTransformationMethod.getInstance()
                change_password_activity_confirm_new_password_edit_text.isEnabled=true
                hidePassword=true
            }
        }


        change_password_activity_update_button.setOnClickListener {
            var email = Firebase.auth.currentUser!!.email.toString()
            var currentPassword = change_password_activity_current_password_edit_text.text.toString()
            var newPassword = change_password_activity_new_password_edit_text.text.toString()
            var confirmPassword =
                change_password_activity_confirm_new_password_edit_text.text.toString()

            if (email.isNotEmpty() && currentPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
            Firebase.auth.signInWithEmailAndPassword(email, currentPassword)
                .addOnSuccessListener {
                    if (newPassword.equals(confirmPassword, ignoreCase = false)) {
                        Firebase.auth.currentUser!!.updatePassword(newPassword)
                            .addOnSuccessListener {
                                Toast.makeText(
                                    this,
                                    "The password was updated. ",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }.addOnFailureListener {
                                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    } else {
                        Toast.makeText(
                            this,
                            "The password and confirm is not matched. ",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                }
        }else{Toast.makeText(
            this,
            "Please fill or data ",
            Toast.LENGTH_SHORT
        ).show()
        }
        }
            }
}