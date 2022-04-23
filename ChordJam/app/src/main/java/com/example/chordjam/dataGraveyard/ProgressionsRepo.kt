package com.example.chordjam.dataGraveyard

import androidx.datastore.core.DataStore
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ProgressionsRepo(private val dataStore: DataStore<Progressions>) {
    suspend fun addProgression(name: String, chords: MutableList<String>){
        val newProgression = Progression(name, chords.toPersistentList())
        dataStore.updateData { it ->
            it.copy(
                userProgressions = it.userProgressions.mutate {
                    it.add(newProgression)
                }
            )
        }
    }

     fun getProgressionAt(index: Int){
        dataStore.data.map { it ->
            it.userProgressions[index]
        }
    }

    val progressions: Flow<PersistentList<Progression>> = dataStore.data
        .catch { exp ->
            if (exp is IOException) {
                emit(ProgressionsSerializer.defaultValue)
            } else {
                throw exp
            }
        }.map { builder->
            builder.userProgressions
        }

}