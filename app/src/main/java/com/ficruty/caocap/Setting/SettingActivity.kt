package com.ficruty.caocap.Setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.ficruty.caocap.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_setting.*

    private var userAuth = FirebaseAuth.getInstance().currentUser

class SettingActivity : AppCompatActivity() {

    private var yourActivity:TextView? = null
    private var notification:TextView? = null
    private var privacy:TextView? = null
    private var security:TextView? = null
    private var ads:TextView? = null
    private var account:TextView? = null
    private var help:TextView? = null
    private var about:TextView? = null
    private var about_activity_item_filter:EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        connectViews()

            Log.d("anas", userAuth?.isEmailVerified.toString())

            if(!userAuth?.isEmailVerified!!){
            setting_acitivty_setting_text_view.text=null;
            setting_acitivty_setting_text_view.layoutParams.height=0
            setting_acitivty_not_verify_message_text_view.text="${Firebase.auth.currentUser!!.email.toString()} is that your email? \nYou don't verity untill now ! ";
            setting_activity_send_verify_button.setOnClickListener(){
                userAuth!!.sendEmailVerification().addOnSuccessListener {

                    Toast.makeText(this,"The verfication sent.",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this,it.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            setting_acitivty_not_verify_message_text_view.text=null;
            setting_activity_send_verify_button.text=null;
            constraintLayout8.layoutParams.height=0
        }

        setting_activity_change_email_button.setOnClickListener(){
            startActivity(Intent(this,ChangeEmailActivity::class.java))
        }

        setting_activity_change_password_button.setOnClickListener(){
            startActivity(Intent(this,ChangePasswordActivity::class.java))
        }

        setting_activity_change_language_button.setOnClickListener(){
            startActivity(Intent(this,ChangeLanguageActivity::class.java))

        }

        about?.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun connectViews() {
        yourActivity = findViewById(R.id.setting_activity_your_activity)
        notification = findViewById(R.id.setting_activity_notification)
        privacy = findViewById(R.id.setting_activity_privacy)
        security = findViewById(R.id.setting_activity_security)
        ads = findViewById(R.id.setting_activity_ads)
        account = findViewById(R.id.setting_activity_account)
        help = findViewById(R.id.setting_activity_help)
        about = findViewById(R.id.setting_activity_about)
        about_activity_item_filter = findViewById(R.id.activity_setting_search_filter)
    }

    private fun prepareListView() {
//            about_activity_item_filter?.addTextChangedListener {
//            arrayAdapter.filter.filter(it)
//           }
    }
}