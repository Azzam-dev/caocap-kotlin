package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ficruty.caocap.Models.Users
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.android.synthetic.main.activity_personal.*

class PersonalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
        getUsername()

    }

    //-------------------------------------------------------------------------------------------
    //  Get the username in the Top of the personal layout.
    private fun getUsername(){
        val uid=Firebase.auth.uid
        val reference=Firebase.database.getReference("users/$uid")
        reference.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val username=p0.getValue(Users::class.java)
                var theusername=username?.username.toString()
                var finalUsername="@$theusername"
                personal_username_text_view.text=finalUsername
            }

        })
    }
    //-------------------------------------------------------------------------------------------
        // Get the user image from file storage.

}




