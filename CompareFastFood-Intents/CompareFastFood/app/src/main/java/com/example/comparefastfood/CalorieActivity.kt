package com.example.comparefastfood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import org.w3c.dom.Text
import android.text.Html

import android.text.method.LinkMovementMethod
import android.widget.Switch


class CalorieActivity : AppCompatActivity() {

    lateinit var calorieTextView : TextView
    lateinit var urlTextView : TextView
    lateinit var diabetes : Switch
    lateinit var worthIt : String
    private var totalCalories : String? = null
    private var url : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calorie)
        worthIt = ""
        //view
        calorieTextView = findViewById<TextView>(R.id.calActTextView)
        urlTextView = findViewById<TextView>(R.id.linkTextView)
        diabetes = findViewById<Switch>(R.id.diabeteSwitch)
        //intent data
        totalCalories = intent.getStringExtra("calorieCount")
        url = intent.getStringExtra("nutritionURL")

       //Log.i("totalCalories","$totalCalories")
        //Log.i("url","$url")

        var calResultText = "The number of calories of your selected meal is: " + totalCalories
        calorieTextView.setText(calResultText)

        urlTextView.setClickable(true);
        urlTextView.setMovementMethod(LinkMovementMethod.getInstance());
        val text = "<a href='" + url + "'>" + "Click here to go to view the nutrition information </a>";
        urlTextView.setText(Html.fromHtml(text));

        if (diabetes.isChecked) {
            worthIt = "(totally worth the diabetes)"
        }
        else {
            worthIt = "(not worth the diabetes)"
        }
    }
    override fun onBackPressed() {
        val data = Intent()
        data.putExtra("worthIt", worthIt)
        setResult(Activity.RESULT_OK, data)
        super.onBackPressed()
        finish()
    }

}