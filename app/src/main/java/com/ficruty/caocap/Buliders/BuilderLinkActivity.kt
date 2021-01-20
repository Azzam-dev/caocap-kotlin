package com.ficruty.caocap.Buliders

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ficruty.caocap.Models.CaocapLink
import com.ficruty.caocap.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_builder_link.*
import kotlinx.android.synthetic.main.activity_edit_profile.*

class BuilderLinkActivity : AppCompatActivity() {

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
        builder_link_blue_border_button.setOnClickListener(){
            caocapColor=2
            borderColor(caocapColor)
        }
        builder_link_green_border_button.setOnClickListener(){
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
            var caocapLink="http://"
            caocapLink+=builder_link_coacap_link_edit_text.text.toString()
            if(caocapName.isNotEmpty() && caocapLink!="http://" ){

            var caocapKey=Firebase.database.getReference("caocap").push().key.toString()
            Firebase.database.getReference("coacap/$caocapKey").setValue(CaocapLink(caocapName,caocapLink,"link",caocapColor,"",true)).addOnSuccessListener {
                Firebase.database.getReference("coaocap/$caocapKey/owners").child("0").setValue(uid).addOnSuccessListener {
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
            }

            }else{
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
    }

}