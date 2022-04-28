package com.example.lab9_ec.data

import android.util.Log
import androidx.datastore.core.DataStore
import com.codelab.android.datastore.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class TodoRepo(private val dataStore: DataStore<Todo>) {
    private val TAG: String = "TodoRepo"
    suspend fun editTodo(title: String, description: String){
        dataStore.updateData { protoBuilder ->
            protoBuilder.toBuilder()
                .clearTitle()
                .clearDescription()
                .setDescription(description)
                .setTitle(title)
                .build()
        }
    }

    val toDo: Flow<MyTodo> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading ProgressionList data.", exception)
                emit(Todo.getDefaultInstance())
            } else {
                throw exception
            }
        }.map { proto ->
            MyTodo(proto.title,proto.description)
        }


}