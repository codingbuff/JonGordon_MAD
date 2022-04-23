package com.example.chordjam

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object ProgSerializer: Serializer<Progression> {
    override val defaultValue: Progression = Progression.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Progression {
        try {
            return Progression.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto serializer.", exception)
        }    }

    override suspend fun writeTo(t: Progression, output: OutputStream) = t.writeTo(output)

}