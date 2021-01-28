package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.ficruty.caocap.R
import com.ficruty.caocap.Services.Language
import kotlinx.android.synthetic.main.activity_change_language.*

class ChangeLanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)

        var languagesArray=arrayOf(Language.AR,Language.EN);

//        change_language_activity_spinner.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,languagesArray);

        change_language_activity_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

}