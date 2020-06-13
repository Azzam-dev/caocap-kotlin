package com.ficruty.caocap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ficruty.caocap.Database.caocap
import com.ficruty.caocap.Database.myCaoDat
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
        val card: caocap = caoData[position]
        val resourceId = context.resources.getIdentifier(card.pic,"drawable",context.packageName)

//        holder.caocapText.text = card.name
//        holder.caocapImage.setImageResource(resourceId)

    }

    inner class ExploreHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var caocapText = itemView.findViewById<TextView>(R.id.caocapText)
//        var caocapImage = itemView.findViewById<ImageView>(R.id.caocapImage)

    }

}