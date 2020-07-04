package com.ficruty.caocap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ficruty.caocap.Database.caocap
import com.ficruty.caocap.Database.mySimpleData
import com.ficruty.caocap.R

class ExploreAdapter(val context:Context,val caoData:List<caocap>): RecyclerView.Adapter<ExploreAdapter.ExploreHolder>() {






    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_item,parent,false)
        return ExploreHolder(view)
    }

    override fun getItemCount(): Int {
        return caoData.size
    }

    override fun onBindViewHolder(holder: ExploreHolder, position: Int) {
//        holder.caocapImage.setImageResource(resourceId)
//        holder.caocapText.text = caocap.name
        val card: caocap = mySimpleData.webCao.caocapList[position]
        //val resourceId = context.resources.getIdentifier(card.pic,"drawable",context.packageName)
        holder.myWebView.loadUrl(card.pic)
        holder.myText.text = card.name

//        holder.caocapText.text = card.name
//        holder.caocapImage.setImageResource(resourceId)
//        val card: webCaocaps = webCao.caocapList[position]
//        holder.myWebView.loadUrl(card.url)

    }

    inner class ExploreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var caocapText = itemView.findViewById<TextView>(R.id.caocapText)
//        var caocapImage = itemView.findViewById<ImageView>(R.id.caocapImage)
        //var caocapWeb:String = "adf"
        val myWebView:WebView = itemView.findViewById<WebView>(R.id.simple_item_caocap_web_view)
        val myText:TextView = itemView.findViewById<TextView>(R.id.simple_item_caocap_name_text_view)

        fun initialize(item:caocap, action:OnCaocapItemListener){

        }

    }

    interface OnCaocapItemListener{
        fun onItemClick(item:caocap, posotion:Int)
    }

}