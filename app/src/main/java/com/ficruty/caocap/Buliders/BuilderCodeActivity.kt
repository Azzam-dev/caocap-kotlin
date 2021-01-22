package com.ficruty.caocap.Buliders

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.ficruty.caocap.R
import kotlinx.android.synthetic.main.activity_builder_code.*

class BuilderCodeActivity : AppCompatActivity() {

    var html="<h1>hola</h1>"
    var css=""
    var js=""

    var venue="html"
    var code="<html><head><style>$css</style></head><body> $html \n $js</body></html>";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_builder_code)

        builder_code_acitivty_HTML_code_button.setTextColor(ContextCompat.getColor(this, R.color.caocapButtonColor))

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

        builder_code_acitivty_test_button.setOnClickListener(){
            saveCode(venue)
            code="<html><head><style>$css</style></head><body> $html \n $js</body></html>"
            builder_code_acitivty_web_view.loadData(code,"text/html","UTF-8")
        }

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
                builder_code_acitivty_HTML_code_button.setTextColor(ContextCompat.getColor(this, R.color.caocapButtonColor))
                builder_code_acitivty_CSS_code_button.setTextColor(Color.parseColor("#000000"))
                builder_code_acitivty_JS_code_button.setTextColor(Color.parseColor("#000000"))
            }
            "css"->{
                builder_code_acitivty_HTML_code_button.setTextColor(Color.parseColor("#000000"))
                builder_code_acitivty_CSS_code_button.setTextColor(ContextCompat.getColor(this, R.color.caocapButtonColor))
                builder_code_acitivty_JS_code_button.setTextColor(Color.parseColor("#000000"))
            }
            "js"->{
                builder_code_acitivty_HTML_code_button.setTextColor(Color.parseColor("#000000"))
                builder_code_acitivty_CSS_code_button.setTextColor(Color.parseColor("#000000"))
                builder_code_acitivty_JS_code_button.setTextColor(ContextCompat.getColor(this, R.color.caocapButtonColor))
            }
        }
    }
}