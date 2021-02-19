package com.ficruty.caocap.Adapter

import android.util.Log
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
        val height = arrayOf(700, 900, 1000).random()
        viewHolder.itemView.simple_item_container_layout.layoutParams.height=height

             viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);


    }
}

class CaocapAdapter_code(var caocap: Caocap,var  dt:DataSnapshot): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item;
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;
        val height = arrayOf(700, 900, 1000).random()
        viewHolder.itemView.simple_item_container_layout.layoutParams.height=height


                val code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\${dt.child("css").value}</style></head><body>\\${dt.child("html").value}<script>\\${dt.child("js").value}</script></body></html>\n"
                Log.d("cods","${dt.child("html").value} \n ${dt.child("css").value} \n ${dt.child("js").value}")
                viewHolder.itemView.simple_item_caocap_web_view.loadData(code,"text/html","UTF-8")


    }
}



class CaocapAdapterShow(var caocap:Caocap): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item
    }
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;
        viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);
        when(caocap.type){
            "link" -> viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);
            "code" ->{
//                var code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\${caocap.code.child("css")}</style></head><body>\\${caocap.code.child("html")}<script>\\${caocap.code.child("js")}</script></body></html>\n"
//                viewHolder.itemView.simple_item_caocap_web_view.loadData(code,"text/html","UTF-8")
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