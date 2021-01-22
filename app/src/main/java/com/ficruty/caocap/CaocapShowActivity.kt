package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ficruty.caocap.Models.Caocap
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_caocap_show.*

class CaocapShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caocap_show)
        var uid=Firebase.auth.uid.toString()
        var caocapLink=intent.getParcelableExtra<Caocap>("caocap_intent")?.link.toString();
        caocap_show_caocap_web_view.loadUrl(caocapLink)
        caocap_show_activity_caocap_name_text_view.setText(intent.getParcelableExtra<Caocap>("caocap_intent")?.link)
        caocap_show_activity_add_to_orrbit_button.setOnClickListener(){
            Firebase.database.getReference("users/$uid/orbiting").child(caocapLink).setValue(caocapLink)
        }
    }
}