package com.izamaralv.swipethebeat.common

import com.izamaralv.swipethebeat.data.entities.Song
import com.izamaralv.swipethebeat.model.DataViewModel
import com.izamaralv.swipethebeat.navigation.Screen

object Constants {
    // lista de pantallas
    val SCREENS = listOf(
        Screen.HomeScreen,
    )

    const val SONGS = "canciones"

    private val likeList = mutableListOf<Song>()
    private val dislikeList = mutableListOf<Song>()
    private val displayList = mutableListOf<Song>()
    private val dataViewModel = DataViewModel()

    fun getLikeList(): List<Song> {
        return likeList.toList()
    }
    fun getDislikeList(): List<Song> {
        return dislikeList.toList()
    }
    fun getDisplayList(): List<Song> {
        return displayList.toList()
    }
    fun addSongLikeList(song: Song) {
        likeList.add(song)
    }
    fun addSongDislikeList(song: Song) {
        dislikeList.add(song)
    }
    fun populateDisplayList() {
        displayList.clear()
        displayList.addAll(dataViewModel.songs.value)
    }
    fun addSongDisplayList(song: Song) {
        displayList.add(song)
    }
    fun deleteSongDisplayList(song: Song) {
        displayList.remove(song)
    }

}