package com.example.chordjam

import android.app.AlertDialog
import android.content.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.View.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chordjam.dataGraveyard.Progressions
import com.example.chordjam.dataGraveyard.ProgressionsRepo
import com.example.chordjam.dataGraveyard.ProgressionsSerializer
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import kotlin.properties.Delegates
private val DATA_STORE_FILE_NAME = "chord_data.pb"

val Context.progDataStore: DataStore<Progression> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = ProgSerializer
)

//val Context.progressionsDataStore: DataStore<Progressions> by dataStore(
//    fileName = "progressions.json",
//    serializer = ProgressionsSerializer
//)

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
    lateinit var draggedView: ImageView
    lateinit var progressionList: ProgItemList
    val CAPACITY = 4
    lateinit var chordProgression: MutableList<ImageView>
    lateinit var chordNames: MutableList<String>
    var nextChordIdx by Delegates.notNull<Int>()
    lateinit var usersSavedProgression: MutableList<String>
    private lateinit var viewModel: ProgViewModel
    //private lateinit var viewModel: ProgressionsViewModel
    private fun saveProgression(chordNames: MutableList<String>): Boolean {
        //stores currently displayed chord progression for later use
        //can only save one progression at a time

        if(!chordNames[0].isNullOrEmpty()){
            viewModel.editProgression(chordNames)
            return true
        }
        //won't save progression if no chords are on screen
        else{
            return false
        }
    }

    //opens window where user can save progression locally
    private fun openSaveDialog() {
        val saveBuilder = AlertDialog.Builder(this@MainActivity)
        saveBuilder.apply {
            setPositiveButton(R.string.save) { _, _ ->
                var progressionSaved = saveProgression(chordNames)
                val snackbar =
                    Snackbar.make(
                        getWindow().getDecorView().getRootView(),
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

    //set clickListener on image views for drag events
    private fun setClickListener(img: ImageView, imgString: String, idxOfChord: Int) {
        //taken from https://proandroiddev.com/drag-and-drop-in-android-all-you-need-to-know-6df8babfb507
        img.setOnLongClickListener { view ->
            val data = ClipData.newPlainText(
                idxOfChord.toString(),
                imgString
            ) //imgString is name of image of the imageView
            val indexData = ClipData.Item(idxOfChord.toString())
            data.addItem(indexData)
            val shadowBuilder = View.DragShadowBuilder(view)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                view.visibility = INVISIBLE
                view.startDragAndDrop(data, shadowBuilder, view, 0)
            }

            true
        }
    }

    //define drag listener events
    private fun setDragListener(img: ImageView, imgIndex: Int) {
        //taken from example in docs: https://developer.android.com/guide/topics/ui/drag-drop#StartDrag
        img.setOnDragListener { v, e ->

            // Handles each of the expected events.
            when (e.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    // Determines if this View can accept the dragged data.
                    if (e.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
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
                    v.setBackgroundResource(R.drawable.left_border)
                    true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    // Ignore the event.
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.setBackgroundResource(0)
                    true
                }
                DragEvent.ACTION_DROP -> {
                    // Gets the item containing the dragged data.
                    v.setBackgroundResource(0)
                    // Gets the text data from the items.
                    val nameItem: ClipData.Item = e.clipData.getItemAt(0)
                    val indexItem: ClipData.Item = e.clipData.getItemAt(1)
                    val chordBeingMoved = nameItem.text.toString()
                    val origIndex = indexItem.text.toString().toInt()
                    //rearrange chords
                    val view = e.localState as View
                    val dropChordIndex = chordProgression.indexOf(v)
                    //rearrange in our chordNames string list so we can utilize string methods
                    rearrangeChordProgression(origIndex, dropChordIndex, chordBeingMoved)
                    view.visibility = VISIBLE
                    // Returns true. DragEvent.getResult() will return true.
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    //below conditional taken from https://stackoverflow.com/questions/10988671/java-util-concurrentmodificationexception-in-view-setvisibility
                    //solves the java.util.ConcurrentModificationException problem
                    //not sure how, but it does
                    if (e.getAction() === DragEvent.ACTION_DRAG_ENDED) {
                        val droppedView = e.getLocalState() as View
                        droppedView.post { droppedView.visibility = VISIBLE }
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

        draggedView = findViewById(R.id.chord1)
        bottomAppBar = findViewById(R.id.bottom_app_bar)
        topText = findViewById<TextView>(R.id.instructionTextView)
        chord1Img = findViewById<ImageView>(R.id.chord1)
        chord2Img = findViewById<ImageView>(R.id.chord2)
        chord3Img = findViewById<ImageView>(R.id.chord3)
        chord4Img = findViewById<ImageView>(R.id.chord4)
        nextChordIdx = 0
        chordProgression = listOf(chord1Img,chord2Img,chord3Img,chord4Img).toMutableList()
        chordNames = listOf("","","","").toMutableList()
        usersSavedProgression = emptyList<String>().toMutableList()
        newChord = ""
        nextChordImg = chordProgression[nextChordIdx]
        progressionList = ProgItemList(mutableListOf(
            ProgItem("My Saved Progression",usersSavedProgression),
            ProgItem("Basic Progression 1", mutableListOf<String>("cmaj","gmaj","amin","fmaj")),
            ProgItem("Basic Progression 2", mutableListOf<String>("gmaj","amin","fmaj","cmaj")),
            ProgItem("Basic Progression 3", mutableListOf<String>("amin","fmaj","cmaj","gmaj")),
            ProgItem("Basic Progression 4", mutableListOf<String>("fmaj","cmaj","gmaj","amin")),
            ProgItem("Doo Wop", mutableListOf<String>("cmaj","amin","fmaj","gmaj")),
            ProgItem("Flamenco", mutableListOf<String>("amin","gmaj","fmaj","emaj")),
            ProgItem("Jazz Standard", mutableListOf<String>("cmaj","amin","dmin","gmaj")),
            ProgItem("La Folia", mutableListOf<String>("amin","e7","amin","gmaj")),
            ProgItem("Pop Rock Lydian", mutableListOf<String>("cmaj","dmaj","fmaj","cmaj")),
            ProgItem("Ragtime", mutableListOf<String>("cmaj","a7","d7","g7")),
            ProgItem("Rock Ballad", mutableListOf<String>("cmaj","emin","fmaj","gmaj"))
        ))
        viewModel = ViewModelProvider(
            this,
            ProgViewModelFactory(ProgRepo(progDataStore))
        )[ProgViewModel::class.java]

        viewModel.progression.observe(this, Observer { progression ->
            usersSavedProgression.clear()
            usersSavedProgression.addAll(progression)
            progressionList.getItemAt(0).chords.clear()
            progressionList.getItemAt(0).chords.addAll(usersSavedProgression)

            Log.d("viewModel observer","usersSavedProgression: $usersSavedProgression")
            Log.d("viewModel observer","progressionList: $progressionList")
        })

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
                    //TODO: open recycyler view
                    val intent = Intent(this, savedProgressions::class.java).apply {
                        putExtra(savedProgressions.PROG_LIST,progressionList)
                        startActivity(this)
                    }
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

    //convert user input to string that represents image resource
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
        //get rid of instructional text once chord is added
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

    //rearrangeChordProgression updates images displayed in chord progression triggered by dragEvent
    //Input: index of chord that's being dragged to rearrange position, the index of the chord in
    //front of it, and the string resource name of the chord that's being dragged
    //Outputs: Nothing, chordProgression list image resources are updated based on user input
    fun rearrangeChordProgression( origIndex: Int, dropChordIndex: Int, chordBeingMoved: String){
        //rearrange in our chordNames string representation first to utilize built-in methods
        //makes it easier to update images in chordProgression list
        if(origIndex + 1 == dropChordIndex){
            return
        }
        chordNames.removeAt(origIndex)
        if(origIndex < dropChordIndex){
            chordNames.add(dropChordIndex - 1, chordBeingMoved)
        }
        else {
            chordNames.add(dropChordIndex, chordBeingMoved)
        }
        //update images using the chordNames list rearranged above
        for ((i,chord) in chordNames.withIndex()){
            if (chordNames[i] == ""){
                break
            }
            val resId = resources.getIdentifier(chordNames[i], "drawable", packageName)
            chordProgression[i].setImageResource(resId)
            setClickListener(chordProgression[i],chordNames[i],i)
            setDragListener(chordProgression[i],i)
        }
    }

    //removeChordFromProgression removes image from chordProgression and updates listeners and
    //image resource for every chord in progression. Method is called upon selection in editDialog
    //Inputs: ImageView of chord to be removed
    //Outputs: Nothing, imageviews in chordProgression list gets updated with the respected
    //imageview resources to reflect change.
    fun removeChordFromProgression(removeChord: ImageView){
        val removeChordIndex = chordProgression.indexOf(removeChord)
        val lastChordPosition = nextChordIdx //can't use nextChordIx to compare so using this instead

        //if there's only one chord in progression, remove it and re-insert instructional text
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
                //reset our click listeners so clipboard data has correct information about chord
                setClickListener(chordProgression[i],chordNames[i],i)
                setDragListener(chordProgression[i],i)
            }
        }
        //set last image to blank and update nextChordIdx so it points to where the next
        //remove click listeners for blank image and update nextChord image and index
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
