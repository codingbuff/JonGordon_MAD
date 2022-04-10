package com.example.chordjam

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates

//
//class DropListener(private val onDrop: () -> Unit) : View.OnDragListener {
//    override fun onDrag(view: View, dragEvent: DragEvent): Boolean {
//        when (dragEvent.action) {
//
//            DragEvent.ACTION_DRAG_ENTERED -> {
//                val snackbar =
//                    Snackbar.make(view, "IMAGE ENTERED DROP AREA", Snackbar.LENGTH_LONG)
//                snackbar.show()
//            }
//            DragEvent.ACTION_DRAG_ENDED -> {
//                val snackbar =
//                    Snackbar.make(view, "ACTION DRAG HAS ENDED", Snackbar.LENGTH_LONG)
//                snackbar.show()
//            }
//            // when item has been dropped, notify about it
//            DragEvent.ACTION_DROP -> onDrop()
//        }
//
//        return true
//    }
//}
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


    private fun setClickListener(img: ImageView, imgString: String){
        //taken from https://proandroiddev.com/drag-and-drop-in-android-all-you-need-to-know-6df8babfb507
        img.setOnLongClickListener{ view ->
            val data = ClipData.newPlainText("", imgString) //imgString is name of image of the imageView
            val shadowBuilder = View.DragShadowBuilder(view)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.startDragAndDrop(data, shadowBuilder, null, 0)
            }
            true
        }
    }

    private fun setDragListener(img: ImageView){
        //taken from example in docs: https://developer.android.com/guide/topics/ui/drag-drop#StartDrag
        img.setOnDragListener{v, e ->

            when (e.action) {

                DragEvent.ACTION_DRAG_STARTED -> {
                    // Determines if this View can accept the dragged data.
                    if (e.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        // As an example of what your application might do, applies a blue color tint
                        // to the View to indicate that it can accept data.
                        //(v as? ImageView)?.setColorFilter(Color.BLUE)

                        // Invalidate the view to force a redraw in the new tint.
                        v.invalidate()
                        Log.i("ACTION_DRAG_STARTED", "drag started fired")

                        // Returns true to indicate that the View can accept the dragged data.
                        true
                    } else {
                        // Returns false to indicate that, during the current drag and drop operation,
                        // this View will not receive events again until ACTION_DRAG_ENDED is sent.
                        false
                    }
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    // Applies a green tint to the View.
                    //(v as? ImageView)?.setColorFilter(Color.GREEN)
                   //val item: ClipData.Item = e.clipData.getItemAt(0)
                    // Invalidates the view to force a redraw in the new tint.
                    //v.invalidate()
                    // Returns true; the value is ignored.
                    Log.i("ACTION_DRAG_ENTERED", "drag entered fired")
                    true
                }

                DragEvent.ACTION_DRAG_LOCATION -> {
            // Ignore the event.
                    Log.i("ACTION_DRAG_LOCATION", "drag location fired")
                    //v.visibility = View.INVISIBLE
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
                    // Gets the text data from the item.
                    val dragData = item.text
                    // Displays a message containing the dragged data.
                  // Toast.makeText(this, "Dragged data is $dragData", Toast.LENGTH_LONG).show()
                    // Turns off any color tints.
                   // (v as? ImageView)?.clearColorFilter()
                    // Invalidates the view to force a redraw.
                    v.invalidate()
                    Log.i("ACTION_DROP", "action drop fired")
                    Log.i("dragData:","$dragData")
                    Log.i("indexOf in ACTION_DROP", chordProgression.indexOf(img).toString())
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
                    Log.i("ACTION_DRAG_ENDED", "drag ended fired")
                   // Log.i("dragData:","$dragData")
                    // Returns true; the value is ignored.
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
        setClickListener(nextChordImg,newChord)
        setDragListener(nextChordImg)
        if (nextChordIdx == 0){
            topText.visibility = View.INVISIBLE
        }

        nextChordIdx += 1
        if(nextChordIdx < CAPACITY){
            nextChordImg = chordProgression[nextChordIdx]
        }
    }

    private fun editChordImage(editImg: ImageView) {
        editChord = getImageString(editChordKey,editChordType)
        val resId = resources.getIdentifier(editChord, "drawable", packageName)
        editImg.setImageResource(resId)
        setClickListener(editImg, editChord)
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
            topText.visibility = View.VISIBLE
        }
        //fill in spots where chord got removed by setting each image to the one in front of it
        else {
            for (i in removeChordIndex until nextChordIdx - 1) {
                chordProgression[i].setImageDrawable(chordProgression[i + 1].drawable)
            }
        }
        //set last image to blank and update nextChordIdx so it points to where the next
        //chord should go
        nextChordIdx -= 1
        chordProgression[nextChordIdx].setImageDrawable(null)
        chordProgression[nextChordIdx].setOnClickListener(null)
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
