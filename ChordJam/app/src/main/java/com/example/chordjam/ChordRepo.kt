package com.example.chordjam

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//interface ChordRepo {
//    suspend fun saveChord(chord: String)
//    suspend fun getChord(): Flow<String>
//}
class ChordRepo(private val chordDataStore: DataStore<Chord>){
    suspend fun saveChord(chord: String) {
        chordDataStore.updateData { store->
            store.toBuilder()
                .setChord(chord)
                .build()
        }
    }

    val myChord: Flow<String> = chordDataStore.data
            .catch { exp ->
                if (exp is IOException){
                    emit(Chord.getDefaultInstance())
                } else {
                    throw exp
                }
            }.map { protoBuilder ->
                protoBuilder.chord
            }

}
