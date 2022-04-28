package com.example.lab9_ec.data

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.codelab.android.datastore.Todo
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object TodoSerializer: Serializer<Todo> {
    override val defaultValue: Todo = Todo.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): Todo {
        try {
            return Todo.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Todo, output: OutputStream) = t.writeTo(output)

}