package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.ExploreAdapter
import com.ficruty.caocap.Database.caocap
import com.ficruty.caocap.Database.mySimpleData
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.android.synthetic.main.simple_item.view.*

class Explore : AppCompatActivity(), ExploreAdapter.OnCaocapItemListener {
//    ,ExploreAdapter.OnCaocapItemListener
    //lateinit var exploreAdapter: ExploreAdapter
    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)


        explore_one_recycler_view.adapter = ExploreAdapter(this, mySimpleData.webCao.caocapList)


        var staggered: StaggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        explore_one_recycler_view.layoutManager = staggered


    }

    override fun onItemClick(item: caocap, posotion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//        adapter.add(Caocap("Google","https://www.google.com.sa"))
//        adapter.add(Caocap("Twitter","https://www.twitter.com"))
//        adapter.add(Caocap("Caocap","https://www.caocap.app"))
//        adapter.add(Caocap("Benq","https://www.benq.com"))
//        adapter.add(Caocap("Absher","https://www.absher.sa"))
//
//        explore_one_recycler_view.adapter=adapter
//        explore_two_recycler_view.adapter=adapter
    }
//}
//class Caocap(var name: String, var link: String) : Item<ViewHolder>() {
//    override fun getLayout(): Int {
//        return R.layout.simple_item
//    }
//
//    override fun bind(viewHolder: ViewHolder, position: Int) {
////        viewHolder.itemView.simple_item_caocap_name_text_view.text = name
//        viewHolder.itemView.simple_item_caocap_web_view.loadUrl(link)
//        // Tall of webView
//        var height = arrayOf(700, 900, 1000).random()
//        viewHolder.itemView.simple_item_card_view.layoutParams.height=height
//        viewHolder.itemView.simple_item_caocap_name_text_view.text=name
//
//
//    }
//}