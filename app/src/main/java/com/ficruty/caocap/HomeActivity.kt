package com.ficruty.caocap

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ficruty.caocap.LoginSignup.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.activity_home.view.home_uid_view

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        checkVersion()
        checkLogin()

        var uid= Firebase.auth.uid.toString()
        home_uid_view.text=uid

        home_button.setOnClickListener(){
            startActivity(Intent(this,Explore::class.java))
        }

        home_button.setOnLongClickListener(){
            startActivity(Intent(this,PersonalActivity::class.java))
            true
        }




    }
    private fun checkVersion(){
        // Get the Version from firebase/appData
        
    }


    private fun checkLogin(){
        val uid=Firebase.auth.uid
        if(uid==null){
            val intent= Intent(this,LoginActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}
