package com.ficruty.caocap

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapterProfileCode
import com.ficruty.caocap.Adapter.CaocapAdapterProfileLink
import com.ficruty.caocap.Auth.LoginActivity
import com.ficruty.caocap.Models.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_personal.*
import kotlinx.android.synthetic.main.activity_personal.builder_link_blue_border_button
import kotlinx.android.synthetic.main.activity_personal.builder_link_green_border_button
import kotlinx.android.synthetic.main.activity_personal.builder_link_orange_border_button
import kotlinx.android.synthetic.main.activity_personal.builder_link_pink_border_button
import kotlinx.android.synthetic.main.activity_personal.builder_link_red_border_button
import kotlinx.android.synthetic.main.activity_personal.builder_link_white_border_button
import kotlinx.android.synthetic.main.builder_choose_menu.view.*
import java.io.ByteArrayOutputStream
import java.util.*
import kotlin.collections.HashMap

class PersonalActivity : AppCompatActivity() {

    var selectImageUri: Uri? = null;
    private var borderColor = 0
    private var adapter = GroupAdapter<ViewHolder>()
    private var auth = FirebaseAuth.getInstance()
    private var animState = false
    private var mFirebaseStorage : FirebaseStorage = FirebaseStorage.getInstance()
    private var html=""
    private var css=""
    private var userAuth = auth.currentUser
    private var js=""

    private var code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\$css</style></head><body>\\$html<script>\\$js</script></body></html>\n"
    private var caocapColor : Int = 0
    private val firstOwner:HashMap<String,String> = HashMap()
    private  var imgUrl : String = ""

    private lateinit var uid: String
    private lateinit var caocapNameCreateSheet: BottomSheetBehavior<ConstraintLayout>
    private lateinit var artbuilderWebViewCreateSheet : BottomSheetBehavior<ConstraintLayout>
    private lateinit var artbuildercodeCreateSheet : BottomSheetBehavior<ConstraintLayout>
    private lateinit var caocapType : String
    private lateinit var caocapName : String
    private lateinit var caocapLink : String
    private lateinit var alertDialog: AlertDialog
    private lateinit var storgeRef : StorageReference




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal)

        checkLogin()

        getUsername()

        inti()

        listeners()

        getMyCaocaps()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0 && resultCode== Activity.RESULT_OK && data != null){

            selectImageUri =data.data
            val bitmap= MediaStore.Images.Media.getBitmap(contentResolver,selectImageUri)
            builder_image_view4.setImageBitmap(bitmap)

            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG,80,outputStream)

           val dataImg : ByteArray  = outputStream.toByteArray()

            val path : String = "caocap-images/" + UUID.randomUUID() + ".png"
            storgeRef = mFirebaseStorage.getReference(path)

           val uploadTask : UploadTask =   storgeRef.putBytes(dataImg)

            val urlTask = uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
               storgeRef.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                     imgUrl = task.result.toString()
                    Log.d("imgUpload", imgUrl)
                }else{Log.d("imgUpload", task.exception.toString()) }

            }



//            val bitmapDrawable= BitmapDrawable(bitmap)
//            builder_image_view4.setBackgroundDrawable(bitmapDrawable)

        }
    }




    private fun venueColor(venue:String){
        when(venue){
            "html"->{
                builder_code_acitivty_HTML_code_button.setImageResource(R.drawable.selected_html)
                builder_code_acitivty_CSS_code_button.setImageResource(R.drawable.css)
                builder_code_acitivty_JS_code_button.setImageResource(R.drawable.js)
            }
            "css"->{
                builder_code_acitivty_HTML_code_button.setImageResource(R.drawable.html)
                builder_code_acitivty_CSS_code_button.setImageResource(R.drawable.selected_css)
                builder_code_acitivty_JS_code_button.setImageResource(R.drawable.js)
            }
            "js"->{
                builder_code_acitivty_HTML_code_button.setImageResource(R.drawable.html)
                builder_code_acitivty_CSS_code_button.setImageResource(R.drawable.css)
                builder_code_acitivty_JS_code_button.setImageResource(R.drawable.selected_js)
            }
        }
    }
    private fun listeners() {

        caocapcode_name_button.setOnClickListener {
            finish()
        }

        caocapcode_name_button.setOnLongClickListener {

            if (!animState) {


                val m = AnimationUtils.loadAnimation(this, R.anim.scale_up)
                val g = AnimationUtils.loadAnimation(this, R.anim.main_fade)

                test_bt.startAnimation(m)
                chat_bt.startAnimation(m)
                art_bt.startAnimation(m)
                Bed.startAnimation(g)

                m.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        test_bt.scaleX = 1f
                        test_bt.scaleY = 1f

                        chat_bt.scaleX = 1f
                        chat_bt.scaleY = 1f

                        art_bt.scaleX = 1f
                        art_bt.scaleY = 1f
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        //placeHolder
                        Log.println(Log.INFO, "ff", "ff")

                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        //placeHolder
                        Log.println(Log.INFO, "ff", "ff")
                    }


                })
                animState = true
            } else {

                val m = AnimationUtils.loadAnimation(this, R.anim.scale_down)
                val y = AnimationUtils.loadAnimation(this, R.anim.main_fadeout)
                art_bt.startAnimation(m)
                Bed.startAnimation(y)
                test_bt.startAnimation(m)
                chat_bt.startAnimation(m)
                animState = false

            }

            true
        }

        personal_menu_button.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }

        personal_create_caocap_button.setOnClickListener {
            val dialogView = LayoutInflater.from(this).inflate(R.layout.builder_choose_menu, null)
            val mBuilder = AlertDialog.Builder(this).setView(dialogView)
            alertDialog = mBuilder.show()

            dialogView.builder_choose_menu_link_button.setOnClickListener {
                clearFields()
                caocapType = "link"

                builder_code_coacap_Link_edit_text5.visibility = View.VISIBLE
                builder_code_coacap_Link_edit_text5.hint = "caoap Link"
                builder_code_create_button5.text = "Publish"
                alertDialog.dismiss()
                caocapNameCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED

                builder_code_create_button5.setOnClickListener {
                    var caocapKey = Firebase.database.getReference("caocap").push().key.toString()
                    caocapName = builder_code_coacap_name_edit_text5.text.toString()
                    caocapLink = builder_code_coacap_Link_edit_text5.text.toString()

                    if (caocapName.isNotEmpty() && caocapLink.isNotEmpty())
                    {

                        Firebase.database.getReference("caocap/$caocapKey").setValue(

                            CaocapLink(
                                caocapName,
                                caocapLink,
                                "link",
                                caocapColor,
                                true,
                                firstOwner,
                                imgUrl
                            )).addOnSuccessListener {
                            caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                            builder_code_coacap_name_edit_text5.text.clear()
                            builder_code_coacap_Link_edit_text5.text.clear()
                            caocapKey = ""
                            Toast.makeText(baseContext,"Your caocap in the Galaxy! ",Toast.LENGTH_LONG).show()
                        }.addOnFailureListener {ex ->
                            caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                            Toast.makeText(baseContext," Something Gone Wrong !! ",Toast.LENGTH_LONG).show()
                            Log.d("caocapUpload",ex.message.toString() )
                        }
                    }else{Toast.makeText(it.context,"Complete Your caocap Information ",Toast.LENGTH_LONG).show()}

                }

            }
            dialogView.builder_choose_menu_code_button.setOnClickListener {
                clearFields()
                caocapType = "code"
                builder_code_create_button5.text = "Create"
                builder_code_coacap_Link_edit_text5.visibility = View.GONE
                alertDialog.dismiss()
                caocapNameCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED
                builder_code_create_button5.setOnClickListener {

                    alertDialog.dismiss()

                    caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED
                    artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    artbuildercodeCreateSheet.peekHeight = 60


                    builder_code_acitivty_code_edit_css.addTextChangedListener {

                        saveTextState()

                        Log.d("anasWebtest" , "html : $html  css  : $css   js : $js" )
                        builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")

                    }
                    builder_code_acitivty_code_edit_text.addTextChangedListener {

                        saveTextState()
                        Log.d("anasWebtest" , "html : $html  css  : $css   js : $js" )
                        builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")
                    }
                    builder_code_acitivty_code_edit_js.addTextChangedListener {

                        saveTextState()
                        Log.d("anasWebtest" , "html : $html  css  : $css   js : $js" )
                        builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")

                    }
                    caocapName = builder_code_coacap_name_edit_text5.text.toString()
                    builder_code_acitivty_publish_button.setOnClickListener {
                        var caocapKey = Firebase.database.getReference("caocap").push().key.toString()

                        val code : HashMap<String,String> = HashMap()

                        code["html"] = html
                        code["css"] = css
                        code["js"] = js

                        Log.d("anas ", "name  : $caocapName  html  : $html  css : $css  js : $js")

                        if (caocapName.isNotEmpty() && html.isNotEmpty())
                        {
                            Firebase.database.getReference("caocap/$caocapKey").setValue(
                                CaocapCode(
                                    caocapName,
                                    code,
                                    caocapType,
                                    caocapColor,
                                    true,
                                    firstOwner,
                                    imgUrl
                                )).addOnSuccessListener {
                                builder_code_coacap_name_edit_text5.text.clear()
                                code.clear()

                                builder_code_acitivty_code_edit_text.text.clear()

                                artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                                Toast.makeText(baseContext,"Your caocap in the Galaxy! ",Toast.LENGTH_LONG).show()
                            }.addOnFailureListener {ex ->
                                artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                                Toast.makeText(baseContext," Something Gone Wrong !! ",Toast.LENGTH_LONG).show()
                                Log.d("caocapUpload",ex.message.toString() )
                            }
                        }else{Toast.makeText(it.context,"Complete Your caocap Information ",Toast.LENGTH_LONG).show()}


                    }

                }

            }
            dialogView.builder_choose_menu_cancel_button.setOnClickListener {

            }
        }








        builder_code_acitivty_HTML_code_button.setOnClickListener{
            builder_code_acitivty_code_edit_css.visibility = View.GONE
            builder_code_acitivty_code_edit_js.visibility = View.GONE
            builder_code_acitivty_code_edit_text.visibility = View.VISIBLE
            venueColor("html")
            saveTextState()
        }

        builder_code_acitivty_CSS_code_button.setOnClickListener{
            builder_code_acitivty_code_edit_css.visibility = View.VISIBLE
            builder_code_acitivty_code_edit_js.visibility = View.GONE
            builder_code_acitivty_code_edit_text.visibility = View.GONE

            venueColor("css")
            saveTextState()
        }

        builder_code_acitivty_JS_code_button.setOnClickListener{
            builder_code_acitivty_code_edit_css.visibility = View.GONE
            builder_code_acitivty_code_edit_js.visibility = View.VISIBLE
            builder_code_acitivty_code_edit_text.visibility = View.GONE

            venueColor("js")
            saveTextState()
        }


        artbuilderWebViewCreateSheet.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN ){
                    artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN

                }


            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
               if (slideOffset == 0.0f){

                   artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                   artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN

               }
            }


        })

        caocapNameCreateSheet.addBottomSheetCallback(object :BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN ){


                }


            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                if (slideOffset == 0.0f){
                    artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN


                }
            }


        })


        web_sheet.setOnClickListener {
            if (artbuildercodeCreateSheet.state == BottomSheetBehavior.STATE_EXPANDED)
            {
                artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            }else {
                artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        builder_link_red_border_button.setOnClickListener{
            caocapColor=0
            borderColor(caocapColor)
        }
        builder_link_orange_border_button.setOnClickListener{
            caocapColor=1
            borderColor(caocapColor)
        }
        builder_link_green_border_button.setOnClickListener{
            caocapColor=2
            borderColor(caocapColor)
        }
        builder_link_blue_border_button.setOnClickListener{
            caocapColor=3
            borderColor(caocapColor)
        }
        builder_link_pink_border_button.setOnClickListener{
            caocapColor=4
            borderColor(caocapColor)
        }
        builder_link_white_border_button.setOnClickListener{
            caocapColor=5
            borderColor(caocapColor)
        }


        builder_image_view4.setOnClickListener{
            val photoIntent = Intent(Intent.ACTION_PICK)
            photoIntent.type = "image/*"
            startActivityForResult(photoIntent, 0)

        }



    }

    private fun clearFields(){
        builder_code_acitivty_code_edit_css.setText("")
        builder_code_acitivty_code_edit_text.setText("")
        builder_code_acitivty_code_edit_js.setText("")
        builder_code_coacap_name_edit_text5.setText("")
        builder_code_coacap_Link_edit_text5.setText("")
    }

    private fun borderColor(number:Int){
        when (number) {
            0 -> builder_image_border4.setBackgroundResource(R.color.editProfileRed)
            1 -> builder_image_border4.setBackgroundResource(R.color.editProfileOrange)
            2 -> builder_image_border4.setBackgroundResource(R.color.editProfileGreen)
            3 -> builder_image_border4.setBackgroundResource(R.color.editProfileBlue)
            4 -> builder_image_border4.setBackgroundResource(R.color.editProfilePink)
            5 -> builder_image_border4.setBackgroundResource(R.color.editProfileWhite)
        }
    }

    private fun saveTextState(){
        css =  builder_code_acitivty_code_edit_css.text.toString()
        js = builder_code_acitivty_code_edit_js.text.toString()
        html = builder_code_acitivty_code_edit_text.text.toString()
    }

    private fun editLinkCaocap( Key: String, Name: String , Link : String,){

        builder_code_coacap_Link_edit_text5.setText(Link)

        builder_code_coacap_name_edit_text5.setText(Name)

        builder_code_coacap_Link_edit_text5.visibility = View.VISIBLE

        builder_code_create_button5.text = " Edit "

        caocapNameCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED

        builder_code_create_button5.setOnClickListener {

            caocapName = builder_code_coacap_name_edit_text5.text.toString()

            caocapLink = builder_code_coacap_Link_edit_text5.text.toString()

            firstOwner.clear()

            firstOwner["0"] = uid

            if (caocapName.isNotEmpty() && caocapLink.isNotEmpty())
            {

                Firebase.database.getReference("caocap/$Key").setValue(

                    CaocapLink(
                        caocapName,
                        caocapLink,
                        "link",
                        caocapColor,
                        true,
                        firstOwner,
                        imgUrl
                    )).addOnSuccessListener {
                    caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    builder_code_coacap_name_edit_text5.text.clear()
                    builder_code_coacap_Link_edit_text5.text.clear()

                    Toast.makeText(baseContext,"Your caocap Has Been Edited ! ",Toast.LENGTH_LONG).show()
                    adapter.clear()
                    getMyCaocaps()
                }.addOnFailureListener {ex ->
                    caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN
                    Toast.makeText(baseContext," Something Gone Wrong !! ",Toast.LENGTH_LONG).show()
                    Log.d("caocapUpload",ex.message.toString() )
                }
            }else{Toast.makeText(it.context,"Complete Your caocap Information ",Toast.LENGTH_LONG).show()}

        }



    }

  private fun editCodeCaocap(Key: String, Name: String, Html : String, Css : String, Js: String, pos : Int){

        builder_code_coacap_name_edit_text5.setText(Name)
        Log.d("anas", "Html : $Html  css : $Css   JS : $Js")
        html = Html
        css = Css
        js = Js

        builder_code_acitivty_code_edit_css.visibility = View.GONE
        builder_code_acitivty_code_edit_js.visibility = View.GONE
        builder_code_acitivty_code_edit_text.visibility = View.VISIBLE

        builder_code_acitivty_code_edit_text.setText(html)
        builder_code_acitivty_code_edit_css.setText(css)
        builder_code_acitivty_code_edit_js.setText(js)

        venueColor("html")

        builder_code_create_button5.text = " Edit "
        builder_code_coacap_Link_edit_text5.visibility = View.GONE

        caocapNameCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED
        builder_code_create_button5.setOnClickListener {

            caocapNameCreateSheet.state = BottomSheetBehavior.STATE_HIDDEN

            artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_EXPANDED

            artbuildercodeCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED

            artbuildercodeCreateSheet.peekHeight = 60

        }

        builder_code_acitivty_publish_button.setOnClickListener {




            val code : HashMap<String,String> = HashMap()

            code["html"] = html

            code["css"] = css

            code["js"] = js

            caocapName = builder_code_coacap_name_edit_text5.text.toString()
            firstOwner.clear()
            firstOwner["0"]= uid
            caocapType = "code"
            if (caocapName.isNotEmpty() && html.isNotEmpty())
            {
                Firebase.database.getReference("caocap/$Key").setValue(

                    CaocapCode(
                        caocapName,
                        code,
                        caocapType,
                        caocapColor,
                        true,
                        firstOwner,
                        imgUrl
                    )).addOnSuccessListener {

                    builder_code_coacap_name_edit_text5.text.clear()
                    code.clear()
                    builder_code_acitivty_code_edit_text.text.clear()
                    builder_code_acitivty_code_edit_css.text.clear()
                    builder_code_acitivty_code_edit_js.text.clear()
                    artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED

                    Toast.makeText(baseContext,"Your Edited caocap in the Galaxy Now! ",Toast.LENGTH_LONG).show()

                    getMyCaocaps()



                }.addOnFailureListener {ex ->
                    artbuilderWebViewCreateSheet.state = BottomSheetBehavior.STATE_COLLAPSED
                    Toast.makeText(baseContext," Something Gone Wrong !! ",Toast.LENGTH_LONG).show()
                    Log.d("caocapUpload",ex.message.toString() )
                }
            }else{Toast.makeText(it.context,"Complete Your caocap Information ",Toast.LENGTH_LONG).show()}


        }

        builder_code_acitivty_code_edit_css.addTextChangedListener {

            saveTextState()
            builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")

        }
        builder_code_acitivty_code_edit_text.addTextChangedListener {

            saveTextState()
            builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")
        }
        builder_code_acitivty_code_edit_js.addTextChangedListener {

            saveTextState()
            builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")

        }

    }

    private fun inti() {
        userAuth?.reload()
        personal_my_caoaps_recycler_view.layoutManager =
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL)

        test_bt.scaleX = 0f
        test_bt.scaleY = 0f

        chat_bt.scaleX = 0f
        chat_bt.scaleY = 0f

        art_bt.scaleX = 0f
        art_bt.scaleY = 0f

        personal_my_caoaps_recycler_view.adapter = adapter

        caocapNameCreateSheet = BottomSheetBehavior.from(caocap_name).apply {
            isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN

        }
         artbuilderWebViewCreateSheet = BottomSheetBehavior.from(web_sheet).apply {
            isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN
        }
        artbuildercodeCreateSheet = BottomSheetBehavior.from(code_sheet).apply {
            isHideable = true
            this.state = BottomSheetBehavior.STATE_HIDDEN
        }


    }


    //-------------------------------------------------------------------------------------------
    //  Get the username in the Top of the personal layout.

    private fun getMyCaocaps() {

        Firebase.database.getReference("caocap").orderByChild("owners/0").equalTo(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("Database error", p0.details)
                }

                override fun onDataChange(p0: DataSnapshot) {
                    adapter.clear()
                    p0.children.forEach {

                        val c = it.getValue(Caocap::class.java)

                        if (c != null) {
                            if (c.type == "link") {
                                adapter.add(CaocapAdapterProfileLink(c , it.key.toString()) { namE, linK,keY ->
                                    editLinkCaocap(keY,namE,linK)

                                })
                            } else {

                                adapter.add(CaocapAdapterProfileCode(c,it.child("code"),it.key.toString()) {keY , namE,htmL,csS,jS ,pos ->
                                    editCodeCaocap(keY,namE,htmL,csS,jS,pos)
                                }

                                )
                            }
                        }
                    }

                    adapter.notifyDataSetChanged()
                }
            })
    }


    private fun getUsername() {

        val reference = Firebase.database.getReference("users/$uid")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                val user = p0.getValue(UserData::class.java)

                val username = user?.username.toString()
                val finalUsername = "@$username"
                personal_username_text_view.text = finalUsername
                val name = user?.name.toString()
                personal_name_text_view.text = name
                val bio = user?.bio.toString()
                personal_bio_text_view.text = bio

                val imageLink = user?.imageURL
                if (!imageLink.isNullOrEmpty()) {
                    Picasso.get().load(imageLink).placeholder(R.drawable.astronaut)
                        .into(personal_profile_image_view)
                }
                borderColor = user?.color.toString().toInt()
                colorOfBorder(borderColor)


            }
        })
    }

    //-------------------------------------------------------------------------------------------
    // Color of border .
    private fun colorOfBorder(number: Int) {
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
    private fun checkLogin() {
        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

        } else {
            uid = auth.currentUser?.uid.toString()
            firstOwner["0"] = uid
        }
    }
}




