package com.izamaralv.swipethebeat.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.izamaralv.swipethebeat.data.entities.Song
import com.izamaralv.swipethebeat.utils.getDataFromFirestore
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {
    private val _songs = mutableStateOf<List<Song>>(emptyList())
    private val _song = mutableStateOf<Song?>(null)
    val songs: State<List<Song>> = _songs
    val song: State<Song?> = _song

    init {
        getData()

    }
    private fun getData() {
        viewModelScope.launch {
            _songs.value = getDataFromFirestore().value
        }
    }

}
