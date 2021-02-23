package com.ficruty.caocap.Buliders

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.ficruty.caocap.Models.CaocapLink
import com.ficruty.caocap.R
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_personal.*
import kotlinx.android.synthetic.main.art_builder_code.*

class BuilderCodeActivity : AppCompatActivity() {

    var html=""
    var css=""
    var js=""

    var venue="html"

    var code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\$css</style></head><body>\\$html<script>\\$js</script></body></html>\n"
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.art_builder_code)


        builder_code_acitivty_HTML_code_button.setOnClickListener(){
            saveCode(venue);
            venue="html"
            venueColor(venue)
            builder_code_acitivty_code_edit_text.setText(html);
        }

        builder_code_acitivty_CSS_code_button.setOnClickListener(){
            saveCode(venue);
            venue="css"
            venueColor(venue)
            builder_code_acitivty_code_edit_text.setText(css);
        }

        builder_code_acitivty_JS_code_button.setOnClickListener(){
            saveCode(venue);
            venue="js"
            venueColor(venue)
            builder_code_acitivty_code_edit_text.setText(js);
        }

//        builder_code_acitivty_test_button.setOnClickListener(){
//            saveCode(venue)
//            code="<!DOCTYPE html><html><head><meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><meta charset=\"utf-8\"><title>CAOCAP</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\" integrity=\"sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z\" crossorigin=\"anonymous\"><style>\\$css</style></head><body>\\$html<script>\\$js</script></body></html>\n"
//            builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")
//
////            val caocapKey = Firebase.database.getReference("caocap").push().key.toString()
////
////            val firstOwner:HashMap<String,String> = HashMap()
////            firstOwner["0"] = uid
////
////
////
////            Firebase.database.getReference("caocap/$caocapKey").setValue(
////                CaocapLink(
////                    caocapName,
////                    caocapLink,
////                    "link",
////                    caocapColor,
////                    "https://",
////                    true,
////                    firstOwner
////                )
//
//        }

    }

    private fun saveCode(venue:String){
        when(venue){
            "html"-> html=builder_code_acitivty_code_edit_text.text.toString();
            "css"-> css=builder_code_acitivty_code_edit_text.text.toString();
            "js"-> js=builder_code_acitivty_code_edit_text.text.toString();
        }
    }

    private fun venueColor(venue:String){
        when(venue){
            "html"->{
                builder_code_acitivty_HTML_code_button.setImageResource(R.drawable.selected_html)
                builder_code_acitivty_CSS_code_button.setImageResource(R.drawable.css)
                builder_code_acitivty_JS_code_button.setImageResource(R.drawable.js)
            }
            "css"->{
                builder_code_acitivty_HTML_code_button.setImageResource(R.drawable.html)
                builder_code_acitivty_CSS_code_button.setImageResource(R.drawable.selected_css)
                builder_code_acitivty_JS_code_button.setImageResource(R.drawable.js)
            }
            "js"->{
                builder_code_acitivty_HTML_code_button.setImageResource(R.drawable.html)
                builder_code_acitivty_CSS_code_button.setImageResource(R.drawable.css)
                builder_code_acitivty_JS_code_button.setImageResource(R.drawable.selected_js)
            }
        }
    }
}