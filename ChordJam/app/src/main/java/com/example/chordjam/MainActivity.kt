package com.example.chordjam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    init var chordImg: ImageView //placeholder chord image/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //views
        //these views are placeholders
        //instead, I will declare 1 image with a placeholder image
        // with a plus sign
        var chord1Img = findViewById<ImageView>(R.id.chord1)
        var chord2Img = findViewById<ImageView>(R.id.chord2)
        var chord3Img = findViewById<ImageView>(R.id.chord3)
        var chord4Img = findViewById<ImageView>(R.id.chord4)



    }

    fun editProgression(view: View) {
        //TODO: instantiate dialog box from: https://developer.android.com/guide/topics/ui/dialogs
        // two spinners: 1) key spinner 2) chord style spinners
        var key = "A, likely" //default key
        var style = "maj, likely" //default style
        //TODO: key = selected spinner option
        //TODO: style = selected spinner option
        //TODO: add button exits dialog box and calls function add new chord
        //TODO: exit button closes dialog box and does no action
        addChord(key, style);
    }
    fun addChord(key: String, style: String){
        //TODO: create new ImageView using syntax: https://stackoverflow.com/questions/41802004/add-imageviews-dynamically-on-android
        //ImageView newChord = new ImageView(AddImageView.this);
        //TODO: convert key, style to image resource
        //var newImgRsrc = turnToAImageFunction(key + someDashOrWhatever + style
        //TODO: instantiate newChord with newImgRsrc and position in scroll view
        //TODO: place new chord in appropriate position with constraints

    }

}