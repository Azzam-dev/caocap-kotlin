package com.ficruty.caocap.Buliders

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import com.ficruty.caocap.Models.CaocapLink
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_builder_link.*
import kotlinx.android.synthetic.main.activity_edit_personal.*

class BuilderLinkActivity : AppCompatActivity() {
    var selectImageUri: Uri? = null;
    var caocapColor:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder_link)
        


        builder_link_red_border_button.setOnClickListener(){
            caocapColor=0
            borderColor(caocapColor)
        }
        builder_link_orange_border_button.setOnClickListener(){
            caocapColor=1
            borderColor(caocapColor)
        }
        builder_link_green_border_button.setOnClickListener(){
            caocapColor=2
            borderColor(caocapColor)
        }
        builder_link_blue_border_button.setOnClickListener(){
            caocapColor=3
            borderColor(caocapColor)
        }
        builder_link_pink_border_button.setOnClickListener(){
            caocapColor=4
            borderColor(caocapColor)
        }
        builder_link_white_border_button.setOnClickListener(){
            caocapColor=5
            borderColor(caocapColor)
        }



        builder_link_create_button.setOnClickListener(){
            var uid=Firebase.auth.uid.toString()
            var caocapName=builder_link_coacap_name_edit_text.text.toString()
            var caocapLink=builder_link_coacap_link_edit_text.text.toString()
            if(caocapName.isNotEmpty() &&caocapLink.isNotEmpty()){
                if(caocapLink.substring(0,7).contains("http://",ignoreCase = true)==true || caocapLink.substring(0,8).contains("https://",ignoreCase = true)==true) {
                    if(Patterns.WEB_URL.matcher(caocapLink).matches()){
                    var caocapKey = Firebase.database.getReference("caocap").push().key.toString()



//                    var pushCaocapMap = mapOf<Any, Any>(
//                        "name" to caocapName,
//                        "link" to caocapLink,
//                        "type" to "link",
//                        "color" to caocapColor,
//                        "imageURL" to "https://",
//                        "published" to true,
//                        "owners" to mapOf<Int, String>(0 to uid)
//                    )



                    Firebase.database.getReference("caocap/$caocapKey").setValue(
                        CaocapLink(
                            caocapName,
                            caocapLink,
                            "link",
                            caocapColor,
                            "https://",
                            true
                        )
                    ).addOnSuccessListener {
//                        Firebase.database.getReference("caocap/$caocapKey/owners").setValue()
                        finish()
                    }.addOnFailureListener {
                        Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"There is and error(s) in your link", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                Toast.makeText(this,"The link must start by http:// or https://",Toast.LENGTH_SHORT).show()
                }
            }
        else{
                Toast.makeText(this,"Please fill name and link",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun borderColor(number:Int){

        when (number) {
            0 -> builder_image_border.setBackgroundResource(R.drawable.edit_profile_red_color)
            1 -> builder_image_border.setBackgroundResource(R.drawable.edit_profile_orange_color)
            2 -> builder_image_border.setBackgroundResource(R.drawable.edit_profile_green_color)
            3 -> builder_image_border.setBackgroundResource(R.drawable.edit_profile_blue_color)
            4 -> builder_image_border.setBackgroundResource(R.drawable.edit_profile_pink_color)
            5 -> builder_image_border.setBackgroundResource(R.drawable.edit_profile_white_color)
        }


        builder_image_view.setOnClickListener(){
            var photoIntent = Intent(Intent.ACTION_PICK);
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data != null){

            selectImageUri =data.data
            var bitmap= MediaStore.Images.Media.getBitmap(contentResolver,selectImageUri);
            builder_image_view.setImageBitmap(bitmap)

            var bitmapDrawable= BitmapDrawable(bitmap)
            builder_image_view.setBackgroundDrawable(bitmapDrawable)

        }
    }
}