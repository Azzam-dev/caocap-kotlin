package com.ficruty.caocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapter
//import com.ficruty.caocap.Adapter.CaocapAdapter
import com.ficruty.caocap.Adapter.ExploreAdapter
import com.ficruty.caocap.Database.caocap
import com.ficruty.caocap.Database.mySimpleData
import com.ficruty.caocap.Models.Caocap
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.android.synthetic.main.simple_item.view.*

class Explore : AppCompatActivity() {
//    ,ExploreAdapter.OnCaocapItemListener
    //lateinit var exploreAdapter: ExploreAdapter
    var adapter = GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)


        explore_one_recycler_view.layoutManager=StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

//        explore_one_recycler_view.adapter = ExploreAdapter(this, mySimpleData.webCao.caocapList)
//        var staggered: StaggeredGridLayoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
//        explore_one_recycler_view.layoutManager = staggered

//        adapter.add(CaocapShow("Google","https"))
//        adapter.add(CaocapShow("Google","https://www.google.com.sa"))
//        adapter.add(CaocapShow("Twitter","https://www.twitter.com"))
//        adapter.add(CaocapShow("Caocap","https://www.caocap.app"))
//        adapter.add(CaocapShow("Benq","https://www.benq.com"))
//        adapter.add(CaocapShow("Absher","https://www.absher.sa"))
//        adapter.add(CaocapShow("dorob","https://dorob.com"))
//        explore_one_recycler_view.adapter=adapter;

//        Firebase.database.getReference("caocap").addChildEventListener(object:ChildEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildChanged(p0: DataSnapshot, p1: String?){
//                val caocap=p0.getValue(Caocap::class.java);
//                if(caocap != null){
//                    if(caocap.type=="link"){
//                        adapter.add(CaocapShow(caocap));
//                    }
//                }
//                explore_one_recycler_view.adapter=adapter;
//            }
//
//            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
//                val caocap=p0.getValue(Caocap::class.java);
//                if(caocap != null){
//                    if(caocap.type=="link"){
//                        adapter.add(CaocapShow(caocap));
//                    }
//                }
//                explore_one_recycler_view.adapter=adapter;
//                adapter.setOnItemClickListener(){item, view ->
//                  val intent=Intent(view.context, PersonalActivity::class.java);
//                  val intenty=item as CaocapShow
//                  intent.putExtra("onetwo",intenty.caocap);
//                  startActivity(intent);
//                }
//            }
//
//            override fun onChildRemoved(p0: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//        })

        Firebase.database.getReference("caocap").addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach(){
                        val caocap=it.getValue(Caocap::class.java);
                        if(caocap != null){
                            if(caocap.type=="link"){
                                adapter.add(CaocapAdapter(caocap));
                            }
                        }
                    }
                         explore_one_recycler_view.adapter=adapter;
                        adapter.setOnItemClickListener(){item, view ->
                            val intent= Intent(view.context,CaocapShowActivity::class.java)
                            val intenty= item as CaocapAdapter;
                            intent.putExtra("caocap_intent_push",intenty.caocap);
                            startActivity(intent);
                        }

            }

        })


    }




////        adapter.add(Caocap("Google","https://www.google.com.sa"))
////        adapter.add(Caocap("Twitter","https://www.twitter.com"))
////        adapter.add(Caocap("Caocap","https://www.caocap.app"))
////        adapter.add(Caocap("Benq","https://www.benq.com"))
////        adapter.add(Caocap("Absher","https://www.absher.sa"))
////
////        explore_one_recycler_view.adapter=adapter
////        explore_two_recycler_view.adapter=adapter
    }
////}
////class Caocap(var name: String, var link: String) : Item<ViewHolder>() {
////    override fun getLayout(): Int {
////        return R.layout.simple_item
////    }
////
////    override fun bind(viewHolder: ViewHolder, position: Int) {
//////        viewHolder.itemView.simple_item_caocap_name_text_view.text = name
////        viewHolder.itemView.simple_item_caocap_web_view.loadUrl(link)
////        // Tall of webView
////        var height = arrayOf(700, 900, 1000).random()
////        viewHolder.itemView.simple_item_card_view.layoutParams.height=height
////        viewHolder.itemView.simple_item_caocap_name_text_view.text=name
////
////
////    }
////}