package com.ficruty.caocap

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.ficruty.caocap.LoginSignup.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        menu_activity_logout_text_view.setOnClickListener(){
            logoutButton()
        }
        menu_activity_logout_image_view.setOnClickListener(){
            logoutButton()
        }
    }

    private fun logoutButton(){
        var buldir=AlertDialog.Builder(this)
        buldir.setTitle("Logout")
        buldir.setMessage("Are you sure to logout? ")
        buldir.setPositiveButton("Yes",{ dialogInterface: DialogInterface, i: Int ->})

        buldir.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
        buldir.show()
    }
}
