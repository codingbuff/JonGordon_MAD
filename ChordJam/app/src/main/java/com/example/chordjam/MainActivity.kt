package com.example.chordjam

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    lateinit var nextChordImg: ImageView
    lateinit var chord1Img: ImageView
    lateinit var chord2Img: ImageView
    lateinit var chord3Img: ImageView
    lateinit var chord4Img: ImageView
    lateinit var addChordKey: String
    lateinit var addChordType: String
    lateinit var newChord: String
    lateinit var chordProgression: MutableList<ImageView>
    var nextChordIdx by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val CAPACITY = 4
        chord1Img = findViewById<ImageView>(R.id.chord1)
        chord2Img = findViewById<ImageView>(R.id.chord2)
        chord3Img = findViewById<ImageView>(R.id.chord3)
        chord4Img = findViewById<ImageView>(R.id.chord4)
        nextChordIdx = 0
        chordProgression = listOf(chord1Img,chord2Img,chord3Img,chord4Img).toMutableList()
        newChord = ""
        nextChordImg = chordProgression[nextChordIdx]
        Log.d("nextChordIx", "$nextChordIdx before call")
        val fab: View = findViewById(R.id.addChordFab)
        fab.setOnClickListener { view ->
            if(nextChordIdx >= CAPACITY){
                val snackbar =
                    Snackbar.make(view, "capacity reached", Snackbar.LENGTH_LONG)
                snackbar.show()
            }
            else{
                openAddDialog(nextChordImg)
            }

        }
    }
    fun createNewChordImage(){
        Log.d("after NewImage", "$nextChordIdx")

        if (addChordKey.length == 1){
            newChord = addChordKey.lowercase() + addChordType
        }
        else {
            newChord = addChordKey[0].lowercase() + "sharp" + addChordKey[3].lowercase() + "flat" + addChordType
        }
        val resId = resources.getIdentifier(newChord, "drawable", packageName)
        nextChordImg.setImageResource(resId)
        nextChordIdx += 1
        if(nextChordIdx < 4){
            nextChordImg = chordProgression[nextChordIdx]
        }
    }

    fun openAddDialog(nextChord: ImageView){

        val addChordDialogView = View.inflate(this@MainActivity, R.layout.add_chord_dialog, null)
        val addChordBuilder = AlertDialog.Builder(this@MainActivity)
        addChordBuilder.setView(addChordDialogView)
        addChordBuilder.apply {
            setPositiveButton(R.string.add){
                _, _->
                val addKeySpinner = addChordDialogView.findViewById<Spinner>(R.id.addKeySpinner)
                val addTypeSpinner = addChordDialogView.findViewById<Spinner>(R.id.addTypeSpinner)
                addChordKey = addKeySpinner.selectedItem.toString()
                addChordType = addTypeSpinner.selectedItem.toString()
                Log.d("before create call", "$nextChordIdx")
                createNewChordImage()
                Log.d("after create call", "$nextChordIdx")
            }
            setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        }
        addChordBuilder.setMessage(R.string.addChordDialogMsg)
            .setTitle(R.string.addChordDialogTitle)
        val addChordDialog = addChordBuilder.create()
        addChordDialog.show()
    }
}
