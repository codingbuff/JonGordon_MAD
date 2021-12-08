package com.example.chordjam

import android.util.Log

class ChordProgression(chordStyle: String, chordPatterns: Array<Pattern>) {
    var style = chordStyle
    private var patterns = chordPatterns

    fun getPattern(selection:String): Pattern {
        return when(selection) {
            "1" -> patterns[0]
            "2" -> patterns[1]
            "3" -> patterns[2]
            "4" -> patterns[3]
            else ->  Pattern(arrayOf(-1,-1,-1,-1),arrayOf("empty","empty","empty","empty"))
        }
    }
}