package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_animation_test.*
import kotlinx.android.synthetic.main.activity_home.*

class AnimationTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation_test)

        animation_button.setOnClickListener(){
        var anim=AnimationUtils.loadAnimation(this,R.anim.animation)
            anim_text.startAnimation(anim)
        }
    }
}