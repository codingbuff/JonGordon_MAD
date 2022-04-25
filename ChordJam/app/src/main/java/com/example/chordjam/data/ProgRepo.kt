package com.example.chordjam.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.example.chordjam.Progression
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ProgRepo(private val dataStore: DataStore<Progression>){

    private val TAG: String = "ProgRepo"

    suspend fun editProgression(chordList: MutableList<String>) {
        dataStore.updateData { pref ->
            pref.toBuilder()
                .clearChords()
                .addAllChords(chordList)
                .build()
        }
    }

    val progressions: Flow<MutableList<String>> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading ProgressionList data.", exception)
                emit(Progression.getDefaultInstance())
            } else {
                throw exception
            }
        }.map { pref ->
            pref.chordsList
        }
}
