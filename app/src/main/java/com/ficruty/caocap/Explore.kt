package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.ExploreAdapter
import com.ficruty.caocap.Database.mySimpleData2
import kotlinx.android.synthetic.main.activity_explore.*

class Explore : AppCompatActivity() {

    //lateinit var exploreAdapter: ExploreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)


        recyclerViewExplore.adapter = ExploreAdapter(this, mySimpleData2.cs)


        var staggered:StaggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerViewExplore.layoutManager = staggered


    }
}
