package com.izamaralv.swipethebeat.utils

import android.util.Log
import com.izamaralv.swipethebeat.common.Constants.getDisplayList
import com.izamaralv.swipethebeat.data.entities.Song

fun getSongByGender(gender: String): Song? {
    val songsByGender = mutableListOf<Song>()
    for (song in getDisplayList()) {
        if (song.genero == gender) {
            songsByGender.add(song)
        }
    }
    if (songsByGender.isEmpty()) return null
    return songsByGender.random()
}

fun getSongByDifferentGender(gender: String): Song? {
    val songsByDifferentGender = mutableListOf<Song>()
    for (song in getDisplayList()) {
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