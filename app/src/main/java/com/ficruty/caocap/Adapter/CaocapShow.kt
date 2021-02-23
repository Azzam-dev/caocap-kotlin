package com.ficruty.caocap.Adapter

import android.content.Intent
import android.util.Log
import com.ficruty.caocap.CaocapShowActivity
import com.ficruty.caocap.Models.Caocap
import com.ficruty.caocap.R
import com.google.firebase.database.DataSnapshot
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.simple_item.view.simple_item_caocap_name_text_view
import kotlinx.android.synthetic.main.simple_item.view.simple_item_caocap_web_view
import kotlinx.android.synthetic.main.simple_item.view.simple_item_container_layout
import kotlinx.android.synthetic.main.simple_item_profile.view.*


class CaocapAdapter(var caocap: Caocap, var oldestCaocapId: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item;
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name
        val height = arrayOf(700, 900, 1000).random()
        viewHolder.itemView.simple_item_container_layout.layoutParams.height=height

        viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link);

        viewHolder.itemView.simple_item_caocap_name_text_view.setOnClickListener {
            val intent = Intent(it.context, CaocapShowActivity::class.java)
            intent.putExtra("caocapName", caocap.name)
            intent.putExtra("oldestCaocapId", oldestCaocapId)
            intent.putExtra("caocapType", caocap.type)
            intent.putExtra("caocapLink", caocap.link)
            it.context.startActivity(intent)
        }
    }
}

class CaocapAdapterCode(var caocap: Caocap, var dt: DataSnapshot, var oldestCaocapId: String): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item;
    }



    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;
        val height = arrayOf(700, 900, 1000).random()
        viewHolder.itemView.simple_item_container_layout.layoutParams.height=height


        val code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\${dt.child(
            "css"
        ).value}</style></head><body>\\${dt.child("html").value}<script>\\${dt.child("js").value}</script></body></html>\n"
        Log.d(
            "cods",
            "${dt.child("html").value} \n ${dt.child("css").value} \n ${dt.child("js").value}"
        )
        viewHolder.itemView.simple_item_caocap_web_view.loadData(code, "text/html", "UTF-8")

        viewHolder.itemView.simple_item_caocap_name_text_view.setOnClickListener {

            val intent  = Intent(it.context, CaocapShowActivity::class.java)
            intent.putExtra("caocapName", caocap.name)
            intent.putExtra("caocapType", caocap.type)
            intent.putExtra("oldestCaocapId", oldestCaocapId)
            intent.putExtra("caocapHtml", dt.child("html").value.toString())
            intent.putExtra("caocapCss", dt.child("css").value.toString())
            intent.putExtra("caocapJs", dt.child("js").value.toString())
            it.context.startActivity(intent)

        }

    }


}

class CaocapAdapterProfileCode(var caocap: Caocap, var dt: DataSnapshot, var key: String,var caocapSelectionListener: (key : String,name : String, html : String,css: String,js : String,pos:Int) -> Unit): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item_profile;
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;

        viewHolder.itemView.simple_item_container_layout.layoutParams.height= 900

        val code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\${dt.child(
            "css"
        ).value}</style></head><body>\\${dt.child("html").value}<script>\\${dt.child("js").value}</script></body></html>\n"

        viewHolder.itemView.simple_item_caocap_web_view.loadData(code , "text/html", "UTF-8")

        viewHolder.itemView.editCaocap.setOnClickListener {
            caocapSelectionListener.invoke(key,caocap.name,dt.child("html").value.toString(),dt.child("css").value.toString(),dt.child("js").value.toString(),position)

        }

    }
}
class CaocapAdapterProfileLink(var caocap: Caocap, var key: String, var caocapSelectionListener: (name: String,link : String,key : String) -> Unit): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.simple_item_profile;
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.simple_item_caocap_name_text_view.text=caocap.name;

        viewHolder.itemView.simple_item_container_layout.layoutParams.height= 900

        viewHolder.itemView.simple_item_caocap_web_view.loadUrl(caocap.link)

        viewHolder.itemView.editCaocap.setOnClickListener {
            caocapSelectionListener.invoke(caocap.name,caocap.link,key)

        }


    }
}