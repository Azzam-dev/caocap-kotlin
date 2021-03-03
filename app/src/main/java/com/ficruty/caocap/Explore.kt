package com.ficruty.caocap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapter
import com.ficruty.caocap.Adapter.CaocapAdapterCode
import com.ficruty.caocap.Auth.LoginActivity
import com.ficruty.caocap.Models.Caocap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.coroutines.*

class Explore : AppCompatActivity() {


    var animState = false
    private val Db: DatabaseReference = Firebase.database.getReference("caocap")
    var adapter = GroupAdapter<ViewHolder>()
    var  fireBaseAuth  = FirebaseAuth.getInstance()


    lateinit var LManger : StaggeredGridLayoutManager
    lateinit var oldestCaocapId : String
    lateinit var scrollListener : EndlessRecyclerViewScrollListener
    lateinit var caocapType :String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

         //vars init
        LManger = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        explore_one_recycler_view.layoutManager = LManger
        explore_one_recycler_view.adapter = adapter


        test_bt.scaleX = 0f
        test_bt.scaleY = 0f

        chat_bt.scaleX = 0f
        chat_bt.scaleY = 0f

        art_bt.scaleX = 0f
        art_bt.scaleY = 0f


        scrollListener = object : EndlessRecyclerViewScrollListener(LManger) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                // Triggered only when new data needs to be appended to the list
              getCaocaps()
            }
        }
        explore_one_recycler_view.addOnScrollListener(scrollListener)
        caocapcode_name_button.setOnClickListener() {
        finish()

        }
        caocapcode_name_button.setOnLongClickListener() {

            if (!animState) {

                opneMain()

            }else{

                closeMsin()
            }

            true
        }

    }


    override fun onStart()
    {

       if (fireBaseAuth.currentUser == null){
           val intent = Intent(this, LoginActivity::class.java)
           intent.flags= Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
           startActivity(intent)
       }else {

//        GlobalScope.launch(Dispatchers.Unconfined) {


           Db.orderByKey().limitToFirst(20)
               .addListenerForSingleValueEvent(object : ValueEventListener {
                   override fun onCancelled(p0: DatabaseError) {

                   }

                   override fun onDataChange(p0: DataSnapshot) {

                       Thread(Runnable {

                           runOnUiThread {
                               p0.children.forEach() {
                                   oldestCaocapId = it.key.toString()
                                   Log.println(Log.INFO, "lastco", oldestCaocapId)
                                   val caocap = it.getValue(Caocap::class.java);

                                   if (caocap != null) {
                                       if (caocap.type == "link") {
                                           adapter.add(CaocapAdapter(caocap, oldestCaocapId),)
                                           caocapType = "link"
                                           adapter.notifyDataSetChanged()
                                       } else {
                                           Log.i("code", it.child("code").toString())
                                           adapter.add(
                                               CaocapAdapterCode(
                                                   caocap,
                                                   it.child("code"), oldestCaocapId
                                               )

                                           )
                                           caocapType = "code"
                                           adapter.notifyDataSetChanged()
                                       }
                                   }
                               }
                           }
                           Log.d("Thread", Thread.currentThread().toString())
                       }).start()


                   }

               })
       }
        super.onStart()
    }

    private fun opneMain(){

        val m = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val g = AnimationUtils.loadAnimation(this, R.anim.main_fade)

        test_bt.startAnimation(m)
        chat_bt.startAnimation(m)
        art_bt.startAnimation(m)
        Bed.startAnimation(g)

        m.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                test_bt.scaleX = 1f
                test_bt.scaleY = 1f

                chat_bt.scaleX = 1f
                chat_bt.scaleY = 1f

                art_bt.scaleX = 1f
                art_bt.scaleY = 1f
            }

            override fun onAnimationEnd(animation: Animation?) {
                test_bt.setOnClickListener() {
                    closeMsin(it)
                    val intent = Intent(it.context, PersonalActivity::class.java)
                    startActivity(intent)
                }
                art_bt.setOnClickListener() {
                    closeMsin(it)

                }
                chat_bt.setOnClickListener() {
                    closeMsin(it)
                }


            }

            override fun onAnimationRepeat(animation: Animation?) {
                //placeHolder
                Log.println(Log.INFO, "ff", "ff")
            }


        })
        animState = true



    }
    private fun closeMsin(it: View){
        val mm = AnimationUtils.loadAnimation(it.context, R.anim.scale_down)
        val yy = AnimationUtils.loadAnimation(it.context, R.anim.main_fadeout)
        art_bt.startAnimation(mm)
        Bed.startAnimation(yy)
        test_bt.startAnimation(mm)
        chat_bt.startAnimation(mm)

        animState = false

    }
    private fun closeMsin(){
        val mm = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        val yy = AnimationUtils.loadAnimation(this, R.anim.main_fadeout)
        art_bt.startAnimation(mm)
        Bed.startAnimation(yy)
        test_bt.startAnimation(mm)
        chat_bt.startAnimation(mm)

        animState = false

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

                        Thread(Runnable {


                            runOnUiThread {
                                p0.children.forEach() {
                                    oldestCaocapId = it.key.toString()
                                    Log.println(Log.INFO, "lastco", oldestCaocapId)
                                    val caocap = it.getValue(Caocap::class.java);

                                    if (caocap != null) {
                                        if (caocap.type == "link") {
                                            adapter.add(CaocapAdapter(caocap,oldestCaocapId))
                                            explore_one_recycler_view.adapter?.notifyDataSetChanged()
                                        } else {
                                            Log.i("code", it.child("code").toString())
                                            adapter.add(CaocapAdapterCode(caocap, it.child("code"),oldestCaocapId))
                                            explore_one_recycler_view.adapter?.notifyDataSetChanged()
                                        }
                                    }
                                }

                            }
                            Log.d("Thread1",Thread.currentThread().toString())
                        }).start()


//

                    }

                })

      //  }


    }
}