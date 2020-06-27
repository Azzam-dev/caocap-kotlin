package com.ficruty.caocap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.view.menu.MenuView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.message_from_user.view.*
import kotlinx.android.synthetic.main.message_to_user.view.*

class ChatLogActivity : AppCompatActivity() {

    val adapter=GroupAdapter<ViewHolder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        adapter.add(DisplayMessageFromUser(oneMessage("Mohammed","10:23 am","This is message to test the design of from user message \n \n hola")))
        adapter.add(DisplayMessageFromUser(oneMessage("Mohammed","10:23 am","This is message to test the design of from user message \n \n hola")))
        adapter.add(DisplayMessageFromUser(oneMessage("Mohammed","10:23 am","This is message to test the design of from user message \n \n hola")))
        chat_log_recycler_view.adapter=adapter

        chat_log_send_message_button.setOnClickListener(){
            var time="00:00"
            var user="USER X"
            var message=chat_log_message_edit_text.text.toString()
            adapter.add(DisplayMessageToUser(oneMessage(user,time,message)))
            chat_log_recycler_view.adapter=adapter
            chat_log_message_edit_text.setText("")
        }
        chat_log_send_message_image_view.setOnClickListener(){
            var time="00:00"
            var user="USER X"
            var message=chat_log_message_edit_text.text.toString()
            adapter.add(DisplayMessageToUser(oneMessage(user,time,message)))
            chat_log_recycler_view.adapter=adapter
            chat_log_message_edit_text.setText("")
        }

    }
}

class DisplayMessageToUser(var message:oneMessage): Item<ViewHolder>(){
    override fun getLayout(): Int {
        return R.layout.message_to_user
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.message_to_user_activity_message_text_view.text=message.message
        viewHolder.itemView.message_to_user_time_of_message_text_view.text=message.time
    }

}

class DisplayMessageFromUser(var message:oneMessage): Item<ViewHolder>(){
    override fun getLayout(): Int {
       return R.layout.message_from_user
    }

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.message_from_user_activity_message_text_view.text=message.message
        viewHolder.itemView.message_from_user_name_text_view.text=message.user
        viewHolder.itemView.message_from_user_time_of_message_text_view.text=message.time
    }

}

class oneMessage(var user:String, var time:String, var message:String){
    constructor():this("","","")
}