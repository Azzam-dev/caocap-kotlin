package com.ficruty.caocap.Setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ficruty.caocap.Adapter.AboutSettingAdapter
import com.ficruty.caocap.Database.AboutActivityContent
import com.ficruty.caocap.Database.AboutData
import com.ficruty.caocap.Database.settingAboutActivityContent
import com.ficruty.caocap.R
import com.ficruty.caocap.Services.IntentParse


class AboutActivity : AppCompatActivity(), AboutSettingAdapter.onItemClickListener {

    private var about_activity_list_item:ListView? = null
    private var aboutData:AboutData? = null
    private var about_activity_recycler_item:RecyclerView? = null
    private var activity_data_policy_text_view:TextView? = null

    //public var name:String? = intent.getStringExtra("SETTINGS")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        connectViews()
        prepareListView()
        prepareRecylerView()

    }

    private fun connectViews() {
        //about_activity_list_item = findViewById(R.id.about_activity_list_view)
        about_activity_recycler_item = findViewById(R.id.about_activity_recycler_view)
        activity_data_policy_text_view = findViewById(R.id.activity_data_policy_text_view)
        //activity_data_policy_text_view?.setText(name)
    }

    private fun prepareListView() {

        val array:ArrayList<AboutData> = ArrayList()
        array.add(AboutData(R.drawable.ic_launcher_background, "data policy"))
        array.add(AboutData(R.drawable.ic_launcher_background, "terms of use"))
        array.add(AboutData(R.drawable.ic_launcher_background, "open source libraries"))

        val array2:ArrayList<String> = ArrayList()
        array2.add("data policy")
        array2.add("terms of use")
        array2.add("open source libraries")

        val arrayAdapter:ArrayAdapter<AboutData> = ArrayAdapter(this,
            R.layout.support_simple_spinner_dropdown_item, array)

        about_activity_list_item?.adapter = arrayAdapter

    }

    private fun prepareRecylerView() {

        val array:ArrayList<AboutData> = ArrayList()
        array.add(AboutData(R.drawable.item_about_activity_data_policy, "data policy"))
        array.add(AboutData(R.drawable.item_about_activity_terms_of_use, "terms of use"))
        array.add(AboutData(R.drawable.item_about_activity_open_source_libraries, "open source libraries"))

        val aboutSettingAdapter:AboutSettingAdapter = AboutSettingAdapter(array, this)
        about_activity_recycler_item?.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        //about_activity_recycler_item?.layoutManager = GridLayoutManager(this,2)
        about_activity_recycler_item?.adapter = aboutSettingAdapter
    }

    override  fun onItemClick(position: Int) {

        // setting lists
        val array:ArrayList<AboutData> = ArrayList()
        array.add(AboutData(R.drawable.item_about_activity_data_policy, "data policy"))
        array.add(AboutData(R.drawable.item_about_activity_terms_of_use, "terms of use"))
        array.add(AboutData(R.drawable.item_about_activity_open_source_libraries, "open source libraries"))

        // data policy lists
        val dataPolicyArray:ArrayList<String> = ArrayList()
        dataPolicyArray.add("this is data policy")
        dataPolicyArray.add("this is terms of use")
        dataPolicyArray.add("this is open source libraries")



        Toast.makeText(this,"item $position clicked",Toast.LENGTH_SHORT).show()
        val clickedItem = array[position]
        val intent: Intent = Intent(this,DataPolicyActivity::class.java)
        intent.putExtra("settings", settingAboutActivityContent[position])
        startActivity(intent)

        Log.i("Entered_Setting_Activity", settingAboutActivityContent[position])
        Log.i("Entered_Setting_Activity", position.toString())


    }


}