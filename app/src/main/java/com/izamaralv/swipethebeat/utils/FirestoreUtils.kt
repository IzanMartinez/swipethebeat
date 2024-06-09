package com.izamaralv.swipethebeat.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.izamaralv.swipethebeat.common.Constants.SONGS
import com.izamaralv.swipethebeat.common.Constants.USERS
import com.izamaralv.swipethebeat.common.Constants.getLikeList
import com.izamaralv.swipethebeat.data.entities.Song
import kotlinx.coroutines.tasks.await

suspend fun getDataFromFirestore(): MutableState<List<Song>> {
    val db = FirebaseFirestore.getInstance()
    val canciones = mutableStateOf<List<Song>>(emptyList())

    try {
        db.collection(SONGS).get().await().map { resultado ->
            canciones.value += resultado.toObject<Song>()
            Log.d("canciones", canciones.value.toString())

        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("firestore error", "getDataFromFirestore: ${e.message}")
    }

    return canciones
}

suspend fun getDataFromSearch(search: String): MutableState<List<Song>> {
    val db = FirebaseFirestore.getInstance()
    val songs = mutableStateOf<List<Song>>(emptyList())

    try {
        db.collection(SONGS)
            .whereEqualTo("nombre", search)
            .whereLessThan("nombre", search + 1)
            .get().await().map { resultado ->
                songs.value += resultado.toObject<Song>()
                Log.d("busquedaCanciones", songs.value.toString())
            }
    } catch (e: FirebaseFirestoreException) {
        Log.d("", ": ${e.message}")
    }

    return songs
}

suspend fun getSongIfLike(gender: String): Song? {
    var isRepe = true
    var isRepeLikeList = false
    var isRepeDislikeList = false
    var index: Int

    val db = FirebaseFirestore.getInstance()
    val songs = mutableStateOf<List<Song>>(emptyList())
    var song: Song? = null

    try {
        db.collection(SONGS)
            .whereEqualTo("genero", gender)
            .get().await().map { result ->
                songs.value += result.toObject<Song>()
                Log.d("generoCanciones", songs.value.toString())
            }
        do {
            index = (0 until songs.value.size).random()
            isRepeLikeList = isSongInList(songs, index, isRepeLikeList)
            isRepeDislikeList = isSongInList(songs, index, isRepeDislikeList)
            if (!isRepeLikeList && !isRepeDislikeList) {
                isRepe = false
                song = songs.value[index]
            }
        } while (isRepe)
    } catch (e: FirebaseFirestoreException) {
        Log.d("", ": ${e.message}")
    }
    return song

}
suspend fun getSongIfDislike(genero: String): Song? {
    var isRepe = true
    var isRepeLikeList = false
    var isRepeDislikeList = false
    var index: Int

    val db = FirebaseFirestore.getInstance()
    val songs = mutableStateOf<List<Song>>(emptyList())
    var song: Song? = null

    try {
        db.collection(SONGS)
            .whereNotEqualTo("genero", genero)
            .get().await().map { resultado ->
                songs.value += resultado.toObject<Song>()
                Log.d("generoCanciones", songs.value.toString())
            }
        do {
            index = (0 until songs.value.size).random()
            isRepeLikeList = isSongInList(songs, index, isRepeLikeList)
            isRepeDislikeList = isSongInList(songs, index, isRepeDislikeList)
            if (!isRepeLikeList && !isRepeDislikeList) {
                isRepe = false
                song = songs.value[index]
            }
        } while (isRepe)
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", ": ${e.message}")
    }
    return song
}

fun addUserDataToCollection(email: String) {
    val db = FirebaseFirestore.getInstance()
    val userData = hashMapOf(
        "email" to email,
        "lista_si" to listOf<String>(),
        "lista_no" to listOf<String>(),
        "nombre_de_usuario" to email.substringBefore("@")
    )
    try {
        db.collection(USERS)
            .add(userData)

    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", ": ${e.message}")
    }
}

private fun isSongInList(
    songs: MutableState<List<Song>>,
    index: Int,
    toCheckList: Boolean
): Boolean {
    var isListed = toCheckList
    for (c in getLikeList()) {
        if (c.link == songs.value[index].link) {
            isListed = true
        }
    }
    return isListed
}

suspend fun getDataRealTime(): MutableState<List<Song>> {
    val db = FirebaseFirestore.getInstance()
    val songs = mutableStateOf<List<Song>>(emptyList())

    try {
        db.collection("canciones")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    songs.value = snapshot.toObjects(Song::class.java)
                }
            }
    } catch (e: FirebaseFirestoreException) {
        Log.d("", ": ${e.message}")
    }

    return songs
}
