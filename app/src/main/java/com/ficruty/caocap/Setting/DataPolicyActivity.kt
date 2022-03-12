package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import com.ficruty.caocap.R
import com.ficruty.caocap.Services.IntentParse

class DataPolicyActivity : AppCompatActivity() {

    private var textView:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_policy)

        connectViews()
    }

    private fun connectViews(){
        textView = findViewById(R.id.activity_data_policy_text_view)
        var i = intent
        val aboutContent = i.getStringExtra("settings")
        textView?.setText(aboutContent)
        textView?.movementMethod = ScrollingMovementMethod()

    }
}