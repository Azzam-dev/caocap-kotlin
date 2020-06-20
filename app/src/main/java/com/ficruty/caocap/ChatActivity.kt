package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import androidx.appcompat.view.menu.MenuView
import com.ficruty.caocap.Models.Users
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.chat_card.view.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        var adapter=GroupAdapter<ViewHolder>()
        adapter.add(TESTADAPTER())
        adapter.add(TESTADAPTER())
        adapter.add(TESTADAPTER())
        adapter.add(TESTADAPTER())

        chat_recycler_view.adapter=adapter
    }
}

class TESTADAPTER():Item<ViewHolder>(){
    override fun getLayout(): Int {
       return R.layout.chat_card
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {

    }
}
