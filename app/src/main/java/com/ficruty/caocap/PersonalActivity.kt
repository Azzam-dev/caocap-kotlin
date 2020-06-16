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
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_personal.*

class PersonalActivity : AppCompatActivity() {

        var borderColor=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
          checkLogin()
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
                var imageLink=user?.imageURL
                if(imageLink != null){
                    Picasso.get().load(imageLink).into(personal_profile_image_view)
                }
                borderColor=user?.color.toString().toInt()
                colorOfBorder(borderColor)



            }
        })
    }

    //-------------------------------------------------------------------------------------------
        // Color of border .
    private fun colorOfBorder(number:Int){
        when (number) {
            0 -> personal_image_border.setBackgroundResource(R.drawable.edit_profile_red_color)
            1 -> personal_image_border.setBackgroundResource(R.drawable.edit_profile_orange_color)
            2 -> personal_image_border.setBackgroundResource(R.drawable.edit_profile_green_color)
            3 -> personal_image_border.setBackgroundResource(R.drawable.edit_profile_blue_color)
            4 -> personal_image_border.setBackgroundResource(R.drawable.edit_profile_pink_color)
            5 -> personal_image_border.setBackgroundResource(R.drawable.edit_profile_white_color)
        }
    }
    //-------------------------------------------------------------------------------------------
    // It's a function to check user login .
    fun checkLogin(){
        var uid=Firebase.auth.uid
        if(uid==null){
          finish()
        }
    }
    //-------------------------------------------------------------------------------------------
        // Get the user image from file storage.

}