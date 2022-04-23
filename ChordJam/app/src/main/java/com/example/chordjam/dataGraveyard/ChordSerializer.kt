//package com.example.chordjam.dataGraveyard
//
//import androidx.datastore.core.CorruptionException
//import androidx.datastore.core.Serializer
//import com.example.chordjam.Chord
//import com.google.protobuf.InvalidProtocolBufferException
//import java.io.InputStream
//import java.io.OutputStream
//
//object ChordSerializer: Serializer<Chord> {
//    override val defaultValue: Chord = Chord.getDefaultInstance()
//
//    override suspend fun readFrom(input: InputStream): Chord {
//        try {
//            return Chord.parseFrom(input)
//        } catch (exception: InvalidProtocolBufferException) {
//            throw CorruptionException("Cannot read proto.", exception)
//        }
//
//    }
//    override suspend fun writeTo(t: Chord, output: OutputStream) = t.writeTo(output)
//
//}