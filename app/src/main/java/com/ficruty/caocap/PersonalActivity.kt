package com.ficruty.caocap

import android.content.Intent
import android.icu.util.Freezable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ficruty.caocap.LoginSignup.LoginActivity
import com.ficruty.caocap.Models.UserData
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_personal.*

class PersonalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
        getUsername()






        personal_menu_button.setOnClickListener(){
            startActivity(Intent(this,MenuActivity::class.java))
        }
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
                val user=p0.getValue(UserData::class.java)
                var username=user?.username.toString()
                var finalUsername="@$username"
                personal_username_text_view.text=finalUsername
                var name=user?.name.toString()
                personal_name_text_view.text=name
                var bio=user?.bio.toString()
                personal_bio_text_view.text=bio
                var imageLink=user?.profileImageLink
                Picasso.get().load(imageLink).into(personal_profile_image_view)
            }
        })
    }
    //-------------------------------------------------------------------------------------------
    // It's a function to check user login .
    fun checkLogin(){
        var uid=Firebase.auth.uid
        if(uid==null){
            var intent= Intent(this, LoginActivity::class.java)
            intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
    //-------------------------------------------------------------------------------------------
        // Get the user image from file storage.

}