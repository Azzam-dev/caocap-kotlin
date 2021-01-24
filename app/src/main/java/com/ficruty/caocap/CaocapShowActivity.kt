package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapterShow
import com.ficruty.caocap.Models.Caocap
import com.ficruty.caocap.Services.IntentParse
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_caocap_show.*

class CaocapShowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caocap_show)
        var uid=Firebase.auth.uid.toString()
        var caocapIntent=IntentParse().caocapShowIntent
        caocap_show_activity_caocap_name_text_view.text=intent.getParcelableExtra<Caocap>(caocapIntent)!!.name

        caocap_show_caocap_web_view.loadUrl(intent.getParcelableExtra<Caocap>(caocapIntent)!!.link)

        var adapter=GroupAdapter<ViewHolder>()

        caocap_show_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        Firebase.database.getReference("caocap").addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach(){
                    var c=it.getValue(Caocap::class.java)
                    if(c!=null){
                        if(c.type=="link"){
                            adapter.add(CaocapAdapterShow(c))
                        }
                    }
                }
                caocap_show_activity_recycler_view.adapter=adapter;
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}