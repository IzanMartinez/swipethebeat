package com.izamaralv.swipethebeat.data.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.izamaralv.swipethebeat.common.Constants.SONGS
import com.izamaralv.swipethebeat.data.entities.Song
import kotlinx.coroutines.tasks.await

class MusicDB {
    private val firestore = FirebaseFirestore.getInstance()
    private val canciones = firestore.collection(SONGS)

    suspend fun getAllCanciones(): List<Song> {
        return try {
            canciones.get().await().toObjects(Song::class.java)
        } catch (e: FirebaseFirestoreException) {
            emptyList()
        }
    }
}