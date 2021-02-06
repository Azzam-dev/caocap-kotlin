package com.ficruty.caocap

//import com.ficruty.caocap.Adapter.CaocapAdapter
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ficruty.caocap.Adapter.CaocapAdapter
import com.ficruty.caocap.Adapter.CaocapAdapter_code
import com.ficruty.caocap.Models.Caocap
import com.ficruty.caocap.Services.IntentParse
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.imangazaliev.circlemenu.CircleMenu
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_explore.*
import kotlinx.android.synthetic.main.simple_item.view.*
import www.sanju.zoomrecyclerlayout.ZoomRecyclerLayout

class Explore : AppCompatActivity() {


    var animState = false
    private var Db: DatabaseReference = Firebase.database.getReference("caocap")
    var adapter = GroupAdapter<ViewHolder>()

    lateinit var LManger : RecyclerView.LayoutManager
    lateinit var oldestCaocapId : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explore)

         //vars init
        LManger = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)



        profile_bt.scaleX = 0f
        profile_bt.scaleY = 0f

        chat_bt.scaleX = 0f
        chat_bt.scaleY = 0f

        explor_bt.scaleX = 0f
        explor_bt.scaleY = 0f




        prfile_activity_caocap_button.setOnClickListener() {

            finish()
        }

        prfile_activity_caocap_button.setOnLongClickListener() {

            if (!animState) {

                opneMain()

            }else{

                val m = AnimationUtils.loadAnimation(this, R.anim.scale_down)
                val y = AnimationUtils.loadAnimation(this, R.anim.main_fadeout)
                explor_bt.startAnimation(m)
                Bed.startAnimation(y)
                profile_bt.startAnimation(m)
                chat_bt.startAnimation(m)

                animState = false
            }

            true
        }

        explore_one_recycler_view.layoutManager = LManger


        // getting the first patch of data
        Handler(Looper.getMainLooper()).postDelayed({
            Db.orderByKey().limitToFirst(10)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        p0.children.forEach() {
                            oldestCaocapId = it.key.toString()
                            Log.println(Log.INFO, "lastco", oldestCaocapId)
                            val caocap = it.getValue(Caocap::class.java);


                            Log.println(Log.INFO, "anas", ""+caocap)

                            if (caocap != null) {
                                if (caocap.type == "link") {
                                    adapter.add(CaocapAdapter(caocap))
                                }else{
                                    Log.i("code" , it.child("code").toString())
                                    adapter.add(CaocapAdapter_code(caocap,it.child("code")))
                                }
                            }
                        }
                        explore_one_recycler_view.adapter = adapter
                        adapter.setOnItemClickListener() { item, view ->
                            val intent = Intent(view.context, CaocapShowActivity::class.java)
                            val intenty = item as CaocapAdapter
                            intent.putExtra(IntentParse().caocapShowIntent, intenty.caocap)
                            startActivity(intent)
                        }

                    }

                })

        }, 1000)

        explore_one_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!recyclerView.canScrollVertically(1)) {

                    getCaocaps()

                }


            }
        })



    }
    private fun opneMain(){
        val m = AnimationUtils.loadAnimation(this, R.anim.scale_up)
        val g = AnimationUtils.loadAnimation(this, R.anim.main_fade)

        profile_bt.startAnimation(m)
        chat_bt.startAnimation(m)
        explor_bt.startAnimation(m)
        Bed.startAnimation(g)

        m.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {
                profile_bt.scaleX = 1f
                profile_bt.scaleY = 1f

                chat_bt.scaleX = 1f
                chat_bt.scaleY = 1f

                explor_bt.scaleX = 1f
                explor_bt.scaleY = 1f
            }

            override fun onAnimationEnd(animation: Animation?) {
                profile_bt.setOnClickListener(){
                    closeMsin(it)
                    val intent = Intent(it.context, PersonalActivity::class.java)
                    startActivity(intent)
                }
                explor_bt.setOnClickListener(){
                   closeMsin(it)

                }
                chat_bt.setOnClickListener(){
                closeMsin(it)
                }


            }

            override fun onAnimationRepeat(animation: Animation?) {
                //placeHolder
                Log.println(Log.INFO,"ff","ff")
            }


        })
        animState = true



    }
    private fun closeMsin(it: View){
        val mm = AnimationUtils.loadAnimation(it.context, R.anim.scale_down)
        val yy = AnimationUtils.loadAnimation(it.context, R.anim.main_fadeout)
        explor_bt.startAnimation(mm)
        Bed.startAnimation(yy)
        profile_bt.startAnimation(mm)
        chat_bt.startAnimation(mm)

        animState = false

    }
    private fun closeMsin(){
        val mm = AnimationUtils.loadAnimation(this, R.anim.scale_down)
        val yy = AnimationUtils.loadAnimation(this, R.anim.main_fadeout)
        explor_bt.startAnimation(mm)
        Bed.startAnimation(yy)
        profile_bt.startAnimation(mm)
        chat_bt.startAnimation(mm)

        animState = false

    }

    private fun getCaocaps(){
        Handler(Looper.getMainLooper()).postDelayed({

            Db.orderByKey().limitToFirst(10).startAt(oldestCaocapId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {

                        p0.children.forEach() {
                            oldestCaocapId = it.key.toString()
                            Log.println(Log.INFO, "lastco1", oldestCaocapId)
                            val caocap = it.getValue(Caocap::class.java);
                            Log.println(Log.INFO, "current_data", caocap.toString())
                            if (caocap != null) {
                                adapter.add(CaocapAdapter(caocap))
                            }
                        }
                        explore_one_recycler_view.adapter = adapter
                        adapter.setOnItemClickListener() { item, view ->
                            val intent = Intent(view.context, CaocapShowActivity::class.java)
                            val intenty = item as CaocapAdapter
                            intent.putExtra(IntentParse().caocapShowIntent, intenty.caocap)
                            startActivity(intent)
                        }

                    }

                })

        }, 1000)
    }
}