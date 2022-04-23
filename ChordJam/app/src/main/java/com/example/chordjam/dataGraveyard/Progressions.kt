package com.example.chordjam.dataGraveyard

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

@Serializable
data class Progressions(
    val userProgressions: PersistentList<Progression> = persistentListOf()
)
@Serializable
data class Progression(
    val name: String,
    val chords: PersistentList<String> = persistentListOf()
)
