package com.example.lab8.util

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.lab8.model.Word
import com.example.lab8.model.WordViewModel
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

//JSON data is good, something wrong with connection to MainActivity
class JSONdata {
    fun loadJSON(context: Context, wordViewModel: WordViewModel, searchWord: String){
        val url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + searchWord
        val queue = Volley.newRequestQueue(context)
        // Request a string response from the provided URL.
        val request = StringRequest(
            Request.Method.GET, url,
            { response ->
                parseJSON(response, wordViewModel)
            },
            {
                Log.e("RESPONSE", error("request failed"))
            }
        )

        // Add the request to the RequestQueue
        queue.add(request)
    }

    //This API is absolutely ridiculous and I really hope that
    //you'll take a gander at this section and see what I had to deal with
    //In short, arrays within objects with arrays within object etc, etc
    //I'm sure there's an easier but hey, it works
    fun parseJSON(response: String, wordViewModel: WordViewModel){
        val dataList = ArrayList<Word>()
        try {
            val jsonArray = JSONArray(response)
            val obj = jsonArray.getJSONObject(0)
            val meanings = obj.getJSONArray("meanings")

            for(i in 0 until meanings.length()){
                val meaningsObj = meanings.getJSONObject(i)
                val defArray = meaningsObj.getJSONArray("definitions")
                val definitionObj = defArray.getJSONObject(0)
                val definition = definitionObj.getString("definition")
                println("definition type ${definition::class.simpleName}")
                Log.d("definition","$definition")
                val newDef = Word(definition)
                dataList.add(newDef)
            }
        }
        catch (e: JSONException) {
            Log.d("exception!","didn't enter try statement")
                e.printStackTrace()
        }
        wordViewModel.updateList(dataList)
    }
}