package com.ficruty.caocap.Setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.ficruty.caocap.R
import com.ficruty.caocap.Services.Language
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_change_language.*

class ChangeLanguageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_language)

        var counter=0;

        var newLanguage=""
        var languageIsNotAvailabe:Boolean=false
        var languagesArray=ArrayList<String>(5);

        languagesArray.add(Language.EN.lan.toString())
        languagesArray.add(Language.AR.lan.toString())

        while(counter<languagesArray.size){
            if(languagesArray[counter].equals(Firebase.auth.languageCode.toString(),ignoreCase = true)){

            }
        }

//        change_language_activity_spinner.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,languagesArray);
        change_language_activity_spinner.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,languagesArray)

        change_language_activity_spinner.onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                newLanguage=parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                newLanguage=Firebase.auth.languageCode.toString()
            }

        }

        change_language_activity_update_button.setOnClickListener(){

                Firebase.auth.setLanguageCode("kh")
//            if(Firebase.auth.languageCode.toString()!=newLanguage){
//                Firebase.auth.setLanguageCode(newLanguage)
//                Toast.makeText(this,"The language was updated\nThe new Language is ${Firebase.auth.languageCode.toString()}",Toast.LENGTH_SHORT).show()
//                finish()
//            }else{
//                Toast.makeText(this,"You didn't change anything.",Toast.LENGTH_SHORT).show()
//
//            }

        }

    }

}