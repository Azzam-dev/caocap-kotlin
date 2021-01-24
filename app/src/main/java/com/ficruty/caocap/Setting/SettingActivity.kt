package com.ficruty.caocap.Setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ficruty.caocap.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_activity_change_email_button.setOnClickListener(){
            startActivity(Intent(this,ChangeEmailActivity::class.java))
        }
    }
}