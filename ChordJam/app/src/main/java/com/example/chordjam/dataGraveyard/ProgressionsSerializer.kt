package com.example.chordjam.dataGraveyard
import androidx.datastore.core.Serializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Suppress("BlockingMethodInNonBlockingContext")
object ProgressionsSerializer: Serializer<Progressions> {
    override val defaultValue: Progressions
        get() = Progressions()

    override suspend fun readFrom(input: InputStream): Progressions {
        return try{
            Json.decodeFromString(
                deserializer = Progressions.serializer(),
                string = input.readBytes().decodeToString()
            )
        } catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }

    }

    override suspend fun writeTo(t: Progressions, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = Progressions.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}