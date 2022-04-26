package com.example.lab9.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepo(private val dataStore: DataStore<Preferences>) {
    private object PreferencesKeys{
        val THOUGHT = stringPreferencesKey("thought")
        val DATE_TIME = stringPreferencesKey("date_time")
    }
    val readFromDataStore: Flow<RandomThought> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.d("DataStoreRepository", exception.message.toString())
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preference ->
            val thought = preference[PreferencesKeys.THOUGHT] ?: ""
            val dateTime = preference[PreferencesKeys.DATE_TIME] ?: ""
            RandomThought(thought, dateTime)
        }


    suspend fun saveToDataStore(thought: String, dateTime:String){
        dataStore.edit { preference ->
            preference[PreferencesKeys.THOUGHT] = thought
            preference[PreferencesKeys.DATE_TIME] = dateTime
        }
    }
}