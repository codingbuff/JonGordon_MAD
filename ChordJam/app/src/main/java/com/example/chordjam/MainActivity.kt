package com.example.chordjam

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*


class MainActivity : AppCompatActivity() {


    lateinit var keySpinner: Spinner
    lateinit var styleSpinner: Spinner
    lateinit var patternSpinner: Spinner
    lateinit var jamButton: Button
    lateinit var firstChordTxtView: TextView
    lateinit var secondChordTxtView: TextView
    lateinit var thirdChordTxtView: TextView
    lateinit var fourthChordTxtView: TextView
    lateinit var firstChordImgView: ImageView
    lateinit var secondChordImgView: ImageView
    lateinit var thirdChordImgView: ImageView
    lateinit var fourthChordImgView: ImageView

    //variables to keep track for rotation
    var image1Id: Int? = null
    var image2Id: Int? = null
    var image3Id: Int? = null
    var image4Id: Int? = null
    var chord1Txt: String = ""
    var chord2Txt: String = ""
    var chord3Txt: String = ""
    var chord4Txt: String = ""


    //create rock patterns
    val rPattern1 = Pattern(arrayOf(1,6,4,5),arrayOf("maj","min","maj","maj"))
    val rPattern2 = Pattern(arrayOf(6,4,1,5),arrayOf("min","maj","maj","maj"))
    val rPattern3 = Pattern(arrayOf(1,6,2,5),arrayOf("maj","min","min","maj"))
    val rPattern4 = Pattern(arrayOf(1,5,6,4),arrayOf("maj","min","maj","maj"))
    //create pop patterns
    val pPattern1 = Pattern(arrayOf(6,4,1,5),arrayOf("min","maj","maj","maj"))
    val pPattern2 = Pattern(arrayOf(1,4,6,5),arrayOf("maj","maj","min","maj"))
    val pPattern3 = Pattern(arrayOf(1,4,5,1),arrayOf("maj","maj","maj","maj"))
    val pPattern4 = Pattern(arrayOf(1,2,5,1),arrayOf("maj","min","maj","maj"))
    //create jazz patterns
    val jPattern1 = Pattern(arrayOf(1,6,2,5),arrayOf("maj7","min7","min7","7"))
    val jPattern2 = Pattern(arrayOf(1,4,3,6),arrayOf("maj7","maj7","min7","7"))
    val jPattern3 = Pattern(arrayOf(1,3,6,2),arrayOf("maj7","7","min7","min7"))
    val jPattern4 = Pattern(arrayOf(2,5,1,1),arrayOf("min7","7","maj7","maj7"))
    //create other patterns
    val oPattern1 = Pattern(arrayOf(1,2,5,4),arrayOf("maj","min","maj","maj"))
    val oPattern2 = Pattern(arrayOf(1,4,2,5),arrayOf("maj","maj","min","maj"))
    val oPattern3 = Pattern(arrayOf(1,3,6,4),arrayOf("maj","min","min","maj"))
    val oPattern4 = Pattern(arrayOf(1,3,6,5),arrayOf("maj","7","min","maj"))
    //create jams
    val rockJams = ChordProgression("Rock", arrayOf(rPattern1,rPattern2,rPattern3,rPattern4))
    val popJams = ChordProgression("Rock", arrayOf(pPattern1,pPattern2,pPattern3,pPattern4))
    val jazzJams = ChordProgression("Rock", arrayOf(jPattern1,jPattern2,jPattern3,jPattern4))
    val otherJams = ChordProgression("Rock", arrayOf(oPattern1,oPattern2,oPattern3,oPattern4))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //views
        keySpinner = findViewById<Spinner>(R.id.keyChoice)
        styleSpinner = findViewById(R.id.styles)
        patternSpinner = findViewById<Spinner>(R.id.patterns)
        jamButton = findViewById<Button>(R.id.jamBtn)
        firstChordTxtView = findViewById<TextView>(R.id.firstChord)
        secondChordTxtView = findViewById<TextView>(R.id.secondChord)
        thirdChordTxtView = findViewById<TextView>(R.id.thirdChord)
        fourthChordTxtView = findViewById<TextView>(R.id.fourthChord)
        firstChordImgView = findViewById<ImageView>(R.id.firstChordImg)
        secondChordImgView = findViewById<ImageView>(R.id.secondChordImg)
        thirdChordImgView = findViewById<ImageView>(R.id.thirdChordImg)
        fourthChordImgView = findViewById<ImageView>(R.id.fourthChordImg)

    }

    //inputs: Pattern objects
    //outputs: None
    //function takes in a pattern and generates the chord symbols declared in main activity
    //for the text views above the chords. Called in the generateChordArray function
    fun generateChordSymbols(ptn: Pattern){
        val romanNumerals = arrayListOf<String>("i","ii","iii","iv","v","vi","vii")
        var chordSymbols = emptyArray<String>()
        val numChordsInPattern = 4
        var i = 0
        while (i < numChordsInPattern){
            chordSymbols += romanNumerals[ptn.order[i] - 1]
            when(ptn.type[i]){
                "maj" -> chordSymbols[i] = chordSymbols[i].uppercase()
                "min" -> {}
                "maj7" -> chordSymbols[i] = chordSymbols[i].uppercase() + "7"
                "min7" -> chordSymbols[i] += "7"
                "7" -> chordSymbols[i] += "\u00B0"
            }
            i += 1
        }
        chord1Txt = chordSymbols[0]
        chord2Txt = chordSymbols[1]
        chord3Txt = chordSymbols[2]
        chord4Txt = chordSymbols[3]
    }

    //inputs: array of strings with the chords in the selected key,Pattern object
    //outputs: array of strings that correspond to a chord chart (stringToID map)
    fun generateChordArray(chordKey: Array<String>,ptn: Pattern): Array<String>{
        generateChordSymbols(ptn)
        var imageChords = emptyArray<String>()
        ptn.order.zip(ptn.type) {idx, typeOfChord -> imageChords += (chordKey[idx-1] + typeOfChord)}
        return imageChords
    }

    fun createJam(view: android.view.View) {

        //chord strings mapped to their drawable ids
        //so we can translate user input to our image views from our drawables
        val stringToID = mapOf(
            "A7" to R.drawable.a7,
            "Amaj7" to R.drawable.amaj7,
            "Amaj" to R.drawable.amaj,
            "Amin" to R.drawable.amin7,
            "Amin7" to R.drawable.amin7,

            "A#/Bb7" to R.drawable.asharp_bflat7,
            "A#/Bbmaj" to R.drawable.asharp_bflatmaj,
            "A#/Bbmaj7" to R.drawable.asharp_bflatmaj7,
            "A#/Bbmin" to R.drawable.asharp_bflatmin,
            "A#/Bbmin7" to R.drawable.asharp_bflatmin7,

            "B7" to R.drawable.b7,
            "Bmaj" to R.drawable.bmaj,
            "Bmaj7" to R.drawable.bmaj7,
            "Bmin" to R.drawable.bmin,
            "Bmin7" to R.drawable.bmin7,

            "C7" to R.drawable.c7,
            "Cmaj" to R.drawable.cmaj,
            "Cmaj7" to R.drawable.cmaj7,
            "Cmin" to R.drawable.cmin,
            "Cmin7" to R.drawable.cmin7,

            "C#/Db7" to R.drawable.csharp_dflat7,
            "C#/Dbmaj7" to R.drawable.csharp_dflatmaj7,
            "C#/Dbmaj" to R.drawable.csharp_dflatmaj,
            "C#/Dbmin" to R.drawable.csharp_dflatmin,
            "C#/Dbmin7" to R.drawable.csharp_dflatmin7,

            "D7" to R.drawable.d7,
            "Dmaj" to R.drawable.dmaj,
            "Dmaj7" to R.drawable.dmaj7,
            "Dmin" to R.drawable.dmin,
            "Dmin7" to R.drawable.dmin7,

            "D#/Eb7" to R.drawable.dsharp_eflat7,
            "D#/Ebmaj7" to R.drawable.dsharp_eflatmaj7,
            "D#/Ebmaj" to R.drawable.dsharp_eflatmaj,
            "D#/Ebmin" to R.drawable.dsharp_eflatmin,
            "D#/Ebmin7" to R.drawable.dsharp_eflatmin7,

            "E7" to R.drawable.e7,
            "Emaj" to R.drawable.emaj,
            "Emaj7" to R.drawable.emaj7,
            "Emin" to R.drawable.emin,
            "Emin7" to R.drawable.emin7,

            "F7" to R.drawable.f7,
            "Fmaj" to R.drawable.fmaj,
            "Fmaj7" to R.drawable.fmaj7,
            "Fmin" to R.drawable.fmin,
            "Fmin7" to R.drawable.fmin7,

            "F#/Gb7" to R.drawable.fsharp_gflat7,
            "F#/Gbmaj7" to R.drawable.fsharp_gflatmaj7,
            "F#/Gbmaj" to R.drawable.fsharp_gflatmaj,
            "F#/Gbmin" to R.drawable.fsharp_gflatmin,
            "F#/Gbmin7" to R.drawable.fsharp_gflatmin7,

            "G7" to R.drawable.g7,
            "Gmaj" to R.drawable.gmaj,
            "Gmaj7" to R.drawable.gmaj7,
            "Gmin" to R.drawable.gmin,
            "Gmin7" to R.drawable.gmin7,

            "G#/Ab7" to R.drawable.gsharp_aflatmaj7,
            "G#/Abmaj7" to R.drawable.gsharp_aflatmaj7,
            "G#/Abmaj" to R.drawable.gsharp_aflatmaj,
            "G#/Abmin" to R.drawable.gsharp_aflatmin,
            "G#/Abmin7" to R.drawable.gsharp_aflatmin7
        )

        //original order of chords
        val context: Context = applicationContext
        val chromaticScale: Array<String> = context.getResources().getStringArray(R.array.keyOptions)

        //user selections
        var selectedKey = keySpinner.selectedItem
        var selectedStyle = styleSpinner.selectedItem
        var selectedPattern = patternSpinner.selectedItem

        //create new chord circle based on user's selected key choice
        val numKeys = 12
        val majorKeyOrder = arrayOf(0,2,4,5,7,9,11) //root whole whole half whole whole whole
        var chromaticAdjusted = emptyArray<String>()
        var userKeyScale = emptyArray<String>()
        var idx = chromaticScale.indexOf(selectedKey)

        while(chromaticAdjusted.size < numKeys){
            if (idx >= numKeys) {
                idx = 0
            }
            chromaticAdjusted += chromaticScale[idx]
            idx += 1
        }
        for (step in majorKeyOrder){
            userKeyScale += chromaticAdjusted[step]
        }

        //Create chord sequence
        var chordSequence = emptyArray<String>()
        when(selectedStyle) {
            "Rock" -> generateChordArray(userKeyScale,rockJams.getPattern(selectedPattern.toString())).forEach { chordSequence += it }
            "Pop" -> generateChordArray(userKeyScale,popJams.getPattern(selectedPattern.toString())).forEach { chordSequence += it }
            "Jazz" -> generateChordArray(userKeyScale,jazzJams.getPattern(selectedPattern.toString())).forEach { chordSequence += it }
            "Other" -> generateChordArray(userKeyScale,otherJams.getPattern(selectedPattern.toString())).forEach { chordSequence += it }
        }

        //set text resources
//        firstChordTxtView.text = chord1Txt
//        secondChordTxtView.text = chord2Txt
//        thirdChordTxtView.text = chord3Txt
//        fourthChordTxtView.text = chord4Txt

        //set image resources
        image1Id = stringToID[chordSequence[0]]
        image2Id = stringToID[chordSequence[1]]
        image3Id = stringToID[chordSequence[2]]
        image4Id = stringToID[chordSequence[3]]

        firstChordImgView.setImageResource(image1Id!!)
        secondChordImgView.setImageResource(image2Id!!)
        thirdChordImgView.setImageResource(image3Id!!)
        fourthChordImgView.setImageResource(image4Id!!)

        updateUI()
    }

    fun updateUI(){
        //TextViews
        firstChordTxtView.text = chord1Txt
        secondChordTxtView.text = chord2Txt
        thirdChordTxtView.text = chord3Txt
        fourthChordTxtView.text = chord4Txt
        //ImageViews
        image1Id?.let { firstChordImgView.setImageResource(it) }
        image2Id?.let { secondChordImgView.setImageResource(it) }
        image3Id?.let { thirdChordImgView.setImageResource(it) }
        image4Id?.let { fourthChordImgView.setImageResource(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("chord1Text",chord1Txt)
        outState.putString("chord2Text",chord2Txt)
        outState.putString("chord3Text",chord3Txt)
        outState.putString("chord4Text",chord4Txt)
        image1Id?.let { outState.putInt("image1", it) }
        image2Id?.let { outState.putInt("image2", it) }
        image3Id?.let { outState.putInt("image3", it) }
        image4Id?.let { outState.putInt("image4", it) }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        chord1Txt = savedInstanceState.getString("chord1Text","")
        chord2Txt = savedInstanceState.getString("chord2Text","")
        chord3Txt = savedInstanceState.getString("chord3Text","")
        chord4Txt = savedInstanceState.getString("chord4Text","")
        image1Id = savedInstanceState.getInt("image1")
        image2Id = savedInstanceState.getInt("image2")
        image3Id = savedInstanceState.getInt("image3")
        image4Id = savedInstanceState.getInt("image4")
        updateUI()
    }
}