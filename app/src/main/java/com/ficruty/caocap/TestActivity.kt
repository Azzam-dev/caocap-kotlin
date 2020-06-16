package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        var url="https://firebasestorage.googleapis.com/v0/b/caocap-x.appspot.com/o/profile-images%2FOBW84dAO1gdme67YhMAWBKEBebU2?alt=media&token=0d812e93-cf0f-44a4-af5f-8a18ac479a14"
        Picasso.get().load(url).into(profile_image)

        var provider=Firebase.auth.currentUser?.providerId
        test_text_view.text=provider.toString()
    }
}
