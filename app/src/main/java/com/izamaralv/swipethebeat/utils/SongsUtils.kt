package com.izamaralv.swipethebeat.utils

import android.util.Log
import com.izamaralv.swipethebeat.data.entities.Song

suspend fun getSongByGender(email: String, gender: String): Song? {
    val songsByGender = mutableListOf<Song>()
    for (song in getDisplayListFromUser(email)) {
        if (song.genero == gender) {
            songsByGender.add(song)
        }
    }
    if (songsByGender.isEmpty()) return null
    return songsByGender.random()
}

suspend fun getSongByDifferentGender(email: String, gender: String): Song? {
    val songsByDifferentGender = mutableListOf<Song>()
    for (song in getDisplayListFromUser(email)) {
        if (song.genero != gender) {
            songsByDifferentGender.add(song)
        }
    }
    if (songsByDifferentGender.isEmpty()) {
        Log.d("getSongByDifferentGender", "No songs found for gender: $gender")
        return null
    }
    return songsByDifferentGender.random()
}