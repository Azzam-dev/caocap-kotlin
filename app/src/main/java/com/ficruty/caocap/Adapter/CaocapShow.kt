package com.ficruty.caocap.Adapter

import com.ficruty.caocap.Models.Caocap
import com.ficruty.caocap.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.simple_item.view.*

class CaocapAdapter(var caocap: Caocap): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item;
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;
        var height = arrayOf(700, 900, 1000).random()
        viewHolder.itemView.simple_item_container_layout.layoutParams.height=height
        when(caocap.type){
            "link" -> viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);
            "code" ->{
                Firebase.database.getReference("caocap").orderByChild("id").equalTo(caocap.name).addListenerForSingleValueEvent(object:
                    ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }
}

class CaocapAdapterPersonal(var caocap:Caocap): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item
    }
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;
        viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);
        when(caocap.type){
            "link" -> viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);
            "code" ->{
                Firebase.database.getReference("caocap").orderByChild("id").equalTo(caocap.name).addListenerForSingleValueEvent(object:
                    ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }
    }
}