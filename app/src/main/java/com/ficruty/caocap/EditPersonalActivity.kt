package com.ficruty.caocap

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import com.ficruty.caocap.Models.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_personal.*

class EditPersonalActivity : AppCompatActivity() {

    var emailOne:String?=""
    var borderColor=-1
    var newColorOfBorder:Int?=null
    var selectPhotoUri: Uri?=null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_personal)

        edit_profile_email_edit_text.isFocusable=false;
        edit_profile_email_edit_text.setOnFocusChangeListener(){view, hasFocus ->
        if(hasFocus){Toast.makeText(this,"You can't change email in this version of app.",Toast.LENGTH_LONG).show()}
        }
        getUserData()



        //-------------------------------------------------------------------------------------------------------------------
        // Buttons of color of border
        edit_profile_red_border_button.setOnClickListener(){
            borderColor=0
            borderColor(borderColor)
            Log.d("borderColor",borderColor.toString())

        }
        edit_profile_orange_border_button.setOnClickListener(){
            borderColor=1
            borderColor(borderColor)
            Log.d("borderColor",borderColor.toString())
        }
        edit_profile_green_border_button.setOnClickListener(){
            borderColor=2
            borderColor(borderColor)
            Log.d("borderColor",borderColor.toString())
        }
        edit_profile_blue_border_button.setOnClickListener(){
            borderColor=3
            borderColor(borderColor)
            Log.d("borderColor",borderColor.toString())
        }
        edit_profile_pink_border_button.setOnClickListener(){
            borderColor=4
            borderColor(borderColor)
            Log.d("borderColor",borderColor.toString())
        }
        edit_profile_white_border_button.setOnClickListener(){
            borderColor=5
            borderColor(borderColor)
            Log.d("borderColor",borderColor.toString())
        }
        //-------------------------------------------------------------------------------------------------------------------


        edit_profile_save_button.setOnClickListener() {
            uploadUserData()
//            uploadeProfileImage()
        }


        //-------------------------------------------------------------------------------------------
        // Select image.
        edit_profile_image_view.setOnClickListener(){
              Toast.makeText(this,"You can't change photo in this version of app",Toast.LENGTH_SHORT).show()
//            var photoIntent= Intent(Intent.ACTION_PICK);
//            photoIntent.type="image/*"
//            startActivityForResult(photoIntent,0)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data != null){

            selectPhotoUri =data.data
            var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,selectPhotoUri);
            edit_profile_image_view.setImageBitmap(bitmap)

            var bitmapDrawable= BitmapDrawable(bitmap)
            edit_profile_image_view.setBackgroundDrawable(bitmapDrawable)

        }
    }
    //    -------------------------------------------------------------------------------------------
    //     Get user information from firebase .
    fun getUserData() {
        var uid = Firebase.auth.uid.toString()
        var reference = Firebase.database.getReference("users/$uid")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                var user = p0.getValue(UserData::class.java)

                var theUsername = user?.username.toString()
                edit_profile_username_edit_text.setText(theUsername)
                var name=user?.name.toString()
                edit_profile_name_edit_text.setText(name)
                var bio=user?.bio.toString()
                edit_profile_bio_edit_text.setText(bio)
                var website=user?.website.toString()
                edit_profile_website_edit_text.setText(website)
                var email = user?.email.toString()
                edit_profile_email_edit_text.setText(email)
                emailOne=email
                var phone=user?.phoneNumber.toString()
                edit_profile_phone_edit_text.setText(phone)
                var imageLink="NoImage"
                imageLink=user?.imageURL.toString()
                if(imageLink != "NoImage"){
                    Picasso.get().load(imageLink).into(edit_profile_image_view)
                }
                borderColor=user?.color.toString().toInt()
                if(borderColor==-1){
                    borderColor(0)
                }else{
                    borderColor(borderColor)
                }
            }
        })
    }

    private fun borderColor(number:Int){
        when (number) {
            0 -> edit_profile_image_border.setBackgroundResource(R.drawable.edit_profile_red_color)
            1 -> edit_profile_image_border.setBackgroundResource(R.drawable.edit_profile_orange_color)
            2 -> edit_profile_image_border.setBackgroundResource(R.drawable.edit_profile_green_color)
            3 -> edit_profile_image_border.setBackgroundResource(R.drawable.edit_profile_blue_color)
            4 -> edit_profile_image_border.setBackgroundResource(R.drawable.edit_profile_pink_color)
            5 -> edit_profile_image_border.setBackgroundResource(R.drawable.edit_profile_white_color)
        }
    }
    //-------------------------------------------------------------------------------------------

    // Upload user data to database .
    private fun uploadUserData() {
        var uid = Firebase.auth.uid
        var reference = Firebase.database.getReference("users/$uid")

        var username = edit_profile_username_edit_text.text.toString()
        var name = edit_profile_name_edit_text.text.toString()
        var bio = edit_profile_bio_edit_text.text.toString()
        var website = edit_profile_website_edit_text.text.toString()
        var email = edit_profile_email_edit_text.text.toString()
        var phone = edit_profile_phone_edit_text.text.toString()

        // Check username and email are empty or not .
        if(username.isEmpty() && email.isEmpty()){
            Toast.makeText(this,"You must fill username and email. ", Toast.LENGTH_SHORT).show()
            return
        }else if(username.isEmpty()){
            Toast.makeText(this,"You must fill username. ", Toast.LENGTH_SHORT).show()
            return
        }else if(email.isEmpty()){
            Toast.makeText(this,"You must fill username. ", Toast.LENGTH_SHORT).show()
            return
        }

        if(email != emailOne){
            Toast.makeText(this,"Sorry, you can not change your email in this version of app. ",
                Toast.LENGTH_SHORT).show()
            return
        }
        // Check length of username .
        if(username.length<4){
            Toast.makeText(this,"The username must be 4 characters or more. ", Toast.LENGTH_SHORT).show()
            return
        }else{
            reference.child("username").setValue(username)
        }

        reference.child("email").setValue(email)

        if (name.isNotEmpty()){
            reference.child("name").setValue(name)
        }
        if (bio.isNotEmpty()){
            reference.child("bio").setValue(bio)
        }
        if (website.isNotEmpty()){
            reference.child("website").setValue(website)
        }
        if (phone.isNotEmpty()){
            reference.child("phoneNumber").setValue(phone)
        }

        //Upload color of border
        if(borderColor != -1){
            reference.child("color").setValue(borderColor)
        }




        Toast.makeText(this,"Your information Update Successfully", Toast.LENGTH_SHORT).show()
    }
    //-------------------------------------------------------------------------------------------
// Upload an image to firebase storage and put the URL in the database realtime .
    private fun uploadeProfileImage(){
        if(selectPhotoUri != null){
            var uid=Firebase.auth.uid.toString()
            var reference=Firebase.storage.reference
            var imageReference=reference.child("profile-images/$uid")
            imageReference.putFile(selectPhotoUri!!).addOnSuccessListener {
                imageReference.downloadUrl.addOnSuccessListener {
                    var imageLink=it.toString()
                    Log.d("TaTana",imageLink)
                    var userReference=Firebase.database.getReference("users/$uid")
                    userReference.child("imageURL").setValue(imageLink)
                }
            }
        }
    }
}