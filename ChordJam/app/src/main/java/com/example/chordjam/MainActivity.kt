package com.example.chordjam

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    lateinit var bottomAppBar: BottomAppBar
    lateinit var topText: TextView
    lateinit var nextChordImg: ImageView
    lateinit var chord1Img: ImageView
    lateinit var chord2Img: ImageView
    lateinit var chord3Img: ImageView
    lateinit var chord4Img: ImageView
    lateinit var addChordKey: String
    lateinit var addChordType: String
    lateinit var editChordKey: String
    lateinit var editChordType: String
    lateinit var newChord: String
    lateinit var editChord: String
    val CAPACITY = 4
    lateinit var chordProgression: MutableList<ImageView>
    lateinit var chordNames: MutableList<String>
    var nextChordIdx by Delegates.notNull<Int>()

    private fun saveProgression(chordProgression: MutableList<ImageView>):Boolean {
        //TODO: implement saveProgression
        //stores currently displayed chord progression for later use
        //will require an edit text view and transferring of data
        return true
    }

    private fun openSaveDialog() {
        val saveBuilder = AlertDialog.Builder(this@MainActivity)
        saveBuilder.apply {
            setPositiveButton(R.string.save) {
                    _, _ ->
                var progressionSaved = saveProgression(chordProgression)
                val snackbar =
                    Snackbar.make(getWindow().getDecorView().getRootView(),
                        if (progressionSaved) R.string.saveSuccess else R.string.saveFail,
                        Snackbar.LENGTH_LONG
                    )
                snackbar.show()
            }
            setNegativeButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        }
        saveBuilder.setMessage(R.string.saveDialogMsg)
            .setTitle(R.string.saveDialogTitle)
        val saveDialog = saveBuilder.create()
        saveDialog.show()
    }


    private fun setClickListener(img: ImageView, imgString: String, idxOfChord: Int){
        //taken from https://proandroiddev.com/drag-and-drop-in-android-all-you-need-to-know-6df8babfb507
        img.setOnLongClickListener{ view ->
            val data = ClipData.newPlainText(idxOfChord.toString(), imgString) //imgString is name of image of the imageView
            val indexData = ClipData.Item(idxOfChord.toString())
            data.addItem(indexData)
            val shadowBuilder = View.DragShadowBuilder(view)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, view, 0)
                view.visibility = INVISIBLE
            }
            true
        }
    }

    private fun setDragListener(img: ImageView, imgIndex: Int){
        //taken from example in docs: https://developer.android.com/guide/topics/ui/drag-drop#StartDrag
        img.setOnDragListener{v, e ->
            when (e.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    // Determines if this View can accept the dragged data.
                    if (e.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        // As an example of what your application might do, applies a blue color tint
                        // to the View to indicate that it can accept data.
                        // Invalidate the view to force a redraw in the new tint.
                        v.invalidate()
                        // Returns true to indicate that the View can accept the dragged data.
                        true
                    } else {
                        // Returns false to indicate that, during the current drag and drop operation,
                        // this View will not receive events again until ACTION_DRAG_ENDED is sent.
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {

                    //(v as? ImageView)?.setColorFilter(Color.GREEN)
                   //val item: ClipData.Item = e.clipData.getItemAt(0)
                    // Invalidates the view to force a redraw in the new tint.
                    //v.invalidate()
                    // Returns true; the value is ignored.
                    true
                }

                DragEvent.ACTION_DRAG_LOCATION -> {
                    // Ignore the event.
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    // Resets the color tint to blue.
                    //(v as? ImageView)?.setColorFilter(Color.BLUE)
                    //val item: ClipData.Item = e.clipData.getItemAt(0)
                    // Gets the text data from the item.
                   // val dragData = item.text
                    // Invalidates the view to force a redraw in the new tint.
                    v.invalidate()
                    // Returns true; the value is ignored.
                    true
                }
                DragEvent.ACTION_DROP -> {
                    // Gets the item containing the dragged data.
                    val item: ClipData.Item = e.clipData.getItemAt(0)
                    val indexItem: ClipData.Item = e.clipData.getItemAt(1)
                    // Gets the text data from the item.
                    val dragData = item.text
                    val idxData = indexItem.text
                    val view = e.getLocalState() as View
                    if (e.getLocalState() != null) {
                        view.visibility = VISIBLE
                    }
                    // Displays a message containing the dragged data.
                  // Toast.makeText(this, "Dragged data is $dragData", Toast.LENGTH_LONG).show()
                    // Turns off any color tints.
                   // (v as? ImageView)?.clearColorFilter()
                    // Invalidates the view to force a redraw.
                    v.invalidate()
                    Log.i("ACTION_DROP","dragData: $dragData")
                    Log.i("ACTION_DROP", "idxData: $idxData")
                    if(e.localState == null){
                        Log.d("localState check", "is null")
                    }
                    else{
                        Log.d("localState check","not null")
                    }
                    //v.visibility = VISIBLE
                    // Returns true. DragEvent.getResult() will return true.
                    true
                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    //val item: ClipData.Item = e.clipData.getItemAt(0)
                    // Gets the text data from the item.
                   // val dragData = item.text
                    // Turns off any color tinting.
                    //(v as? ImageView)?.clearColorFilter()

                    // Invalidates the view to force a redraw.
                    //v.invalidate()

                    // Does a getResult(), and displays what happened.
//                    when (e.result) {
//                        true ->
//                            Toast.makeText(this, "The drop was handled.", Toast.LENGTH_LONG)
//                        else ->
//                            Toast.makeText(this, "The drop didn't work.", Toast.LENGTH_LONG)
//                    }.show()
//                    v.visibility = View.VISIBLE
                   // Log.i("dragData:","$dragData")
                    // Returns true; the value is ignored.
                    val view = e.getLocalState() as View
                    if (e.getLocalState() != null) {
                        view.visibility = VISIBLE
                    }
                    true
                }
                else -> {
                    // An unknown action type was received.
                    Log.e(
                        "DragDrop Example",
                        "Unknown action type received by View.OnDragListener."
                    )
                    false
                }
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomAppBar = findViewById(R.id.bottom_app_bar)
        topText = findViewById<TextView>(R.id.instructionTextView)
        chord1Img = findViewById<ImageView>(R.id.chord1)
        chord2Img = findViewById<ImageView>(R.id.chord2)
        chord3Img = findViewById<ImageView>(R.id.chord3)
        chord4Img = findViewById<ImageView>(R.id.chord4)
        nextChordIdx = 0
        chordProgression = listOf(chord1Img,chord2Img,chord3Img,chord4Img).toMutableList()
        chordNames = listOf("","","","").toMutableList()
        newChord = ""
        nextChordImg = chordProgression[nextChordIdx]
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

        bottomAppBar.setOnMenuItemClickListener{ menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_folder -> {
                    Toast.makeText(this,"you clicked Load",Toast.LENGTH_LONG).show()
                    true
                }
                R.id.app_bar_save -> {
                    openSaveDialog()
                    true
                }
                else -> false
            }
        }
    }

    fun getImageString(key: String, type: String): String {
        var returnString = ""
        if (key.length == 1){
            returnString = key.lowercase() + type
        }
        else {
            returnString = key[0].lowercase() + "sharp" + key[3].lowercase() + "flat" + type
        }
        return returnString
    }

    fun createNewChordImage(){
        newChord = getImageString(addChordKey,addChordType)
        val resId = resources.getIdentifier(newChord, "drawable", packageName)
        nextChordImg.setImageResource(resId)
        var newImg = nextChordImg
        nextChordImg.setOnClickListener{openEditDialog(newImg) }
        setClickListener(nextChordImg,newChord,nextChordIdx)
        setDragListener(nextChordImg,nextChordIdx)

        if (nextChordIdx == 0){
            topText.visibility = View.INVISIBLE
        }
        chordNames[nextChordIdx] = newChord
        nextChordIdx += 1
        if(nextChordIdx < CAPACITY){
            nextChordImg = chordProgression[nextChordIdx]
        }
    }

    private fun editChordImage(editImg: ImageView) {
        editChord = getImageString(editChordKey,editChordType)
        val resId = resources.getIdentifier(editChord, "drawable", packageName)
        editImg.setImageResource(resId)
        var indexInProgression = chordProgression.indexOf(editImg)
        chordNames[indexInProgression] = editChord
        setClickListener(editImg, editChord, indexInProgression)
        setDragListener(editImg,indexInProgression)
    }

    private fun openEditDialog(editImg: ImageView) {
        val editChordDialogView = View.inflate(this@MainActivity, R.layout.select_chord_dialog, null)
        val editChordBuilder = AlertDialog.Builder(this@MainActivity)
        editChordBuilder.setView(editChordDialogView)
        editChordBuilder.apply {
            setPositiveButton(R.string.apply){
                    _, _->
                val editKeySpinner = editChordDialogView.findViewById<Spinner>(R.id.addKeySpinner)
                val editTypeSpinner = editChordDialogView.findViewById<Spinner>(R.id.addTypeSpinner)
                editChordKey = editKeySpinner.selectedItem.toString()
                editChordType = editTypeSpinner.selectedItem.toString()
                editChordImage(editImg)
            }

            setNegativeButton(R.string.removeChord){
                _, _->
                removeChordFromProgression(editImg)
            }
            setNeutralButton(R.string.cancel,
                DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                })
        }
        editChordBuilder.setMessage(R.string.addChordDialogMsg)
            .setTitle(R.string.editChordDialogTitle)

        val editChordDialog = editChordBuilder.create()
        editChordDialog.show()
    }

    fun removeChordFromProgression(removeChord: ImageView){
        val removeChordIndex = chordProgression.indexOf(removeChord)
        val lastChordPosition = nextChordIdx //can't use nextChordIx to compare so doing this

        //if there's only one chord in progression, remove it and re-insert
        //instructional text
        if (lastChordPosition < 2) {
            chordProgression[0].setImageDrawable(null)
            chordNames[0] = ""
            topText.visibility = View.VISIBLE
        }
        //fill in spots where chord got removed by setting each image to the one in front of it
        else {
            for (i in removeChordIndex until nextChordIdx - 1) {
                chordProgression[i].setImageDrawable(chordProgression[i + 1].drawable)
                chordNames[i] = chordNames[i+1]
                setClickListener(chordProgression[i],chordNames[i],i)
                setDragListener(chordProgression[i],i)
            }
        }
        //set last image to blank and update nextChordIdx so it points to where the next
        //chord should go
        nextChordIdx -= 1
        chordProgression[nextChordIdx].setImageDrawable(null)
        chordNames[nextChordIdx] = ""
        chordProgression[nextChordIdx].setOnClickListener(null)
        chordProgression[nextChordIdx].setOnDragListener(null)
        chordProgression[nextChordIdx].setOnLongClickListener(null)
        nextChordImg = chordProgression[nextChordIdx]
    }

    fun openAddDialog(nextChord: ImageView){
        val addChordDialogView = View.inflate(this@MainActivity, R.layout.select_chord_dialog, null)
        val addChordBuilder = AlertDialog.Builder(this@MainActivity)
        addChordBuilder.setView(addChordDialogView)
        addChordBuilder.apply {
            setPositiveButton(R.string.add){
                _, _->
                val addKeySpinner = addChordDialogView.findViewById<Spinner>(R.id.addKeySpinner)
                val addTypeSpinner = addChordDialogView.findViewById<Spinner>(R.id.addTypeSpinner)
                addChordKey = addKeySpinner.selectedItem.toString()
                addChordType = addTypeSpinner.selectedItem.toString()
                createNewChordImage()
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
