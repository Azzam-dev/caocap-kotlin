package com.ficruty.caocap.Controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.ficruty.caocap.Adapter.ExploreAdapter
//import com.ficruty.caocap.Adapter.ExploreAdapter
import com.ficruty.caocap.Database.caocap
import com.ficruty.caocap.Database.mySimpleData
import com.ficruty.caocap.Database.mySimpleData2
import com.ficruty.caocap.R
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var exploreAdapter:ExploreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val exploreAdapter = ExploreAdapter(this, mySimpleData2.cs)
//        explore.adapter = exploreAdapter
//        explore.layoutManager = GridLayoutManager(this,2)
//        recyclerViewExplore.layoutManager
//        recyclerViewExplore.adapter

    }
}
