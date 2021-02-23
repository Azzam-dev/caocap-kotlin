package com.ficruty.caocap

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapter
import com.ficruty.caocap.Adapter.CaocapAdapterCode
import com.ficruty.caocap.Models.Caocap
import com.ficruty.caocap.Services.IntentParse
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_caocap_show.*
import kotlinx.android.synthetic.main.activity_explore.*

class CaocapShowActivity : AppCompatActivity() {
    val adapter = GroupAdapter<ViewHolder>()
    private var Db: DatabaseReference = Firebase.database.getReference("caocap")

    private lateinit var css : String
    private lateinit var html : String
    private lateinit var js: String
    private lateinit var caocapName: String
    private lateinit var caocapType : String
    private lateinit var oldestCaocapId: String
    private lateinit var caocapLink: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caocap_show)

        caocap_show_activity_recycler_view.adapter=adapter;

        inti()
        if (caocapType == "link"){
            caocapLink = intent.getStringExtra(IntentParse.instance.caocapLink)!!

            caocap_show_activity_caocap_name_text_view.text = caocapName
            caocap_show_caocap_web_view.loadUrl(caocapLink)

        }
        if (caocapType == "code") {
            css = intent.getStringExtra(IntentParse.instance.caocapCss)!!
            html = intent.getStringExtra(IntentParse.instance.caocapHtml)!!
            js = intent.getStringExtra(IntentParse.instance.caocapJs)!!

            val code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> " +
                    "   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\">" +
                    "<title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\"" +
                    " crossorigin=\"anonymous\"><style>\\${css}</style></head><body>\\${html}<script>\\${js
            }</script></body></html>\n"

            caocap_show_caocap_web_view.loadData(code,"text/html","UTF-8")
            caocap_show_activity_caocap_name_text_view.text = caocapName

        }
        caocap_show_activity_recycler_view.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        getCaocaps()
    }

    private fun inti() {
         caocapType = intent.getStringExtra(IntentParse.instance.caocapType)!!

         oldestCaocapId = intent.getStringExtra(IntentParse.instance.oldestCaocapId)!!
         caocapName = intent.getStringExtra( IntentParse.instance.caocapName)!!
    }

    private fun getCaocaps(){


        //  GlobalScope.launch(Dispatchers.Unconfined) {
        //  yield()
        Db.orderByKey().startAt(oldestCaocapId).limitToFirst(20)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    Log.d("explore onCancelled data base", "msg : ${p0.message}")
                }

                override fun onDataChange(p0: DataSnapshot) {

                    Thread(kotlinx.coroutines.Runnable {


                        runOnUiThread {
                            p0.children.forEach() {
                                oldestCaocapId = it.key.toString()
                                Log.println(Log.INFO, "lastco", oldestCaocapId)
                                val caocap = it.getValue(Caocap::class.java);

                                if (caocap != null) {
                                    if (caocap.type == "link") {
                                        adapter.add(CaocapAdapter(caocap, oldestCaocapId))

                                    } else {
                                        Log.i("code", it.child("code").toString())
                                        adapter.add(
                                            CaocapAdapterCode(
                                                caocap,
                                                it.child("code"),
                                                oldestCaocapId
                                            )
                                        )

                                    }
                                }
                            }

                        }
                        Log.d("Thread1", Thread.currentThread().toString())
                    }).start()


                }

            })

        //  }


    }
}