package com.example.chordjam

import androidx.datastore.core.DataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

//class ChordRepoImpl(
//    private val chordDataStore: DataStore<Chord>
//): ChordRepo {
//    override suspend fun saveChord(chord: String) {
//        chordDataStore.updateData { store->
//            store.toBuilder()
//                .setChord(chord)
//                .build()
//        }
//    }
//
//    override suspend fun getChord(): Flow<String> {
//        return chordDataStore.data
//            .catch { exp ->
//                if (exp is IOException){
//                    emit(Chord.getDefaultInstance())
//                } else {
//                    throw exp
//                }
//            }.map { protoBuilder ->
//                protoBuilder.chord
//            }
//    }
//
//}
