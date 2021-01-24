package com.ficruty.caocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapterPersonal
import com.ficruty.caocap.Buliders.BuilderCodeActivity
import com.ficruty.caocap.Buliders.BuilderLinkActivity
import com.ficruty.caocap.Models.Caocap
import com.ficruty.caocap.Models.UserData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_personal.*
import kotlinx.android.synthetic.main.builder_choose_menu.view.*

class PersonalActivity : AppCompatActivity() {

        var borderColor=0
        var adapter=GroupAdapter<ViewHolder>();
        var uid=Firebase.auth.uid.toString();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)
          checkLogin()
          getUsername()
           personal_my_caoaps_recycler_view.layoutManager=StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);


        caocap_button.setOnClickListener(){
            finish()
        }

        caocap_button.setOnLongClickListener(){
            // Not yet
            true
        }

        personal_menu_button.setOnClickListener(){
            startActivity(Intent(this,MenuActivity::class.java))
        }

        personal_create_caocap_button.setOnClickListener(){
            val dialogView=LayoutInflater.from(this).inflate(R.layout.builder_choose_menu,null);
            val mBuilder=AlertDialog.Builder(this).setView(dialogView)
            var alertDialog=mBuilder.show()

            dialogView.builder_choose_menu_link_button.setOnClickListener(){
                startActivity(Intent(this, BuilderLinkActivity::class.java))
            }
            dialogView.builder_choose_menu_code_button.setOnClickListener(){
                startActivity(Intent(this, BuilderCodeActivity::class.java))
            }
            dialogView.builder_choose_menu_cancel_button.setOnClickListener(){
                alertDialog.dismiss()
            }
        }
        // Just for test
        getMyCaocaps()
    }

//    override fun onRestart() {
//        super.onRestart()
//        getMyCaocaps()
//    }

//    override fun onResume() {
//        super.onResume()
//        getMyCaocaps()
//    }

//    override fun onStart() {
//        super.onStart()
//        getMyCaocaps()
//    }

    //-------------------------------------------------------------------------------------------
    //  Get the username in the Top of the personal layout.

    private fun getMyCaocaps(){
        Firebase.database.getReference("caocap").orderByChild("owners/0").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach(){
                    val c=it.getValue(Caocap::class.java);
                    if(c!=null){
                        if(c.type=="link"){
                            adapter.add(CaocapAdapterPersonal(c));
                        }
                    }
                }
                personal_my_caoaps_recycler_view.adapter=adapter;
            }
        })
    }

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

