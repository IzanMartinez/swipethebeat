package com.izamaralv.swipethebeat.utils

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.toObject
import com.izamaralv.swipethebeat.common.Constants.SONGS
import com.izamaralv.swipethebeat.common.Constants.USERS
import com.izamaralv.swipethebeat.common.Constants.getLikeList
import com.izamaralv.swipethebeat.data.entities.Song
import com.izamaralv.swipethebeat.ui.theme.GreenName
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
        db.collection(SONGS).whereEqualTo("nombre", search).whereLessThan("nombre", search + 1)
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
        db.collection(SONGS).whereEqualTo("genero", gender).get().await().map { result ->
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
        db.collection(SONGS).whereNotEqualTo("genero", genero).get().await().map { resultado ->
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

suspend fun getSongsIdsFromCollection(): List<String> {
    val db = FirebaseFirestore.getInstance()

    return try {
        // Retrieve all song documents from SONGS collection
        val songsQuery = db.collection(SONGS).get().await()

        // Extract link values (song IDs) from the documents
        val songIds = songsQuery.documents.mapNotNull { doc ->
            doc.getString("link") // Assuming "link" is the field name in your SONGS collection
        }

        songIds
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error retrieving songIds from SONGS collection: ${e.message}")
        emptyList()
    }
}

suspend fun addUserDataToCollection(email: String) {
    val db = FirebaseFirestore.getInstance()

    try {
        // Get songIds from SONGS collection
        val songIds = getSongsIdsFromCollection()

        // Create user data with lista_a_mostrar initialized with song IDs (links)
        val userData = hashMapOf(
            "email" to email,
            "lista_si" to listOf<String>(),
            "lista_no" to listOf<String>(),
            "nombre_de_usuario" to email.substringBefore("@"),
            "lista_a_mostrar" to songIds,
            "tema" to GreenName
        )

        // Add user data to USERS collection
        db.collection(USERS).add(userData).await()

        Log.d("FIREBASE", "User added successfully with lista_a_mostrar populated")

    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error adding user data to USERS collection: ${e.message}")
    }
}

suspend fun getLikeListFromUser(email: String): List<Song> {
    val db = FirebaseFirestore.getInstance()
    val songIds = mutableListOf<String>()

    try {
        // Get the lista_si array which contains song IDs
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            val userDoc = userQuery.documents[0]
            val listaSi = userDoc.get("lista_si") as? List<String>

            // Assuming lista_si is a list of song IDs (strings)
            if (listaSi != null && listaSi.isNotEmpty()) {
                songIds.addAll(listaSi)
            }
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error retrieving like list: ${e.message}")
    }

    // Fetch Song objects corresponding to songIds
    val songs = mutableListOf<Song>()
    songIds.forEach { songId ->
        try {
            val songQuery = db.collection(SONGS).whereEqualTo("link", songId).get().await()

            if (!songQuery.isEmpty) {
                val song = songQuery.documents[0].toObject<Song>()
                if (song != null) {
                    songs.add(song)
                }
            } else {
                Log.d("FIREBASE", "Song not found for ID: $songId")
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("FIREBASE", "Error fetching song: ${e.message}")
        }
    }

    return songs
}


suspend fun getDislikeListFromUser(email: String): List<Song> {
    val db = FirebaseFirestore.getInstance()
    val songIds = mutableListOf<String>()

    try {
        // Get the lista_no array which contains song IDs
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            val userDoc = userQuery.documents[0]
            val listaNo = userDoc.get("lista_no") as? List<String>

            // Assuming lista_si is a list of song IDs (strings)
            if (listaNo != null && listaNo.isNotEmpty()) {
                songIds.addAll(listaNo)
            }
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error retrieving dislike list: ${e.message}")
    }

    // Fetch Song objects corresponding to songIds
    val songs = mutableListOf<Song>()
    songIds.forEach { songId ->
        try {
            val songQuery = db.collection(SONGS).whereEqualTo("link", songId).get().await()

            if (!songQuery.isEmpty) {
                val song = songQuery.documents[0].toObject<Song>()
                if (song != null) {
                    songs.add(song)
                }
            } else {
                Log.d("FIREBASE", "Song not found for ID: $songId")
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("FIREBASE", "Error fetching song: ${e.message}")
        }
    }

    return songs
}

fun getSongId(song: Song): String {
    return song.link
}

suspend fun addSongToUserLikeList(email: String, song: Song) {
    val db = FirebaseFirestore.getInstance()
    val songId = getSongId(song)

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0].reference

            // Update the lista_si array with the new song ID
            userDoc.update("lista_si", FieldValue.arrayUnion(songId)).await()
            Log.d("FIREBASE", "Song added successfully to lista_si")
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error adding song to lista_si: ${e.message}")
    }
}

suspend fun deleteSongFromUserLikeList(email: String, song: Song) {
    val db = FirebaseFirestore.getInstance()
    val songId = getSongId(song)

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0].reference

            // Update the lista_si array by removing the song ID
            userDoc.update("lista_si", FieldValue.arrayRemove(songId)).await()
            Log.d("FIREBASE", "Song removed successfully from lista_si")
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error removing song from lista_si: ${e.message}")
    }
}

suspend fun addSongToUserDislikeList(email: String, song: Song) {
    val db = FirebaseFirestore.getInstance()
    val songId = getSongId(song)

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0].reference

            // Update the lista_si array with the new song ID
            userDoc.update("lista_no", FieldValue.arrayUnion(songId)).await()
            Log.d("FIREBASE", "Song added successfully to lista_no")
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error adding song to lista_no: ${e.message}")
    }
}

suspend fun getDisplayListFromUser(email: String): List<Song> {
    val db = FirebaseFirestore.getInstance()
    val songIds = mutableListOf<String>()

    try {
        // Get the lista_a_mostrar array which contains song IDs
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            val userDoc = userQuery.documents[0]
            val listaMostrar = userDoc.get("lista_a_mostrar") as? List<String>

            // Assuming lista_a_mostrar is a list of song IDs (strings)
            if (listaMostrar != null && listaMostrar.isNotEmpty()) {
                songIds.addAll(listaMostrar)
            }
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error retrieving display list: ${e.message}")
    }

    // Fetch Song objects corresponding to songIds
    val songs = mutableListOf<Song>()
    songIds.forEach { songId ->
        try {
            val songQuery = db.collection(SONGS).whereEqualTo("link", songId).get().await()

            if (!songQuery.isEmpty) {
                val song = songQuery.documents[0].toObject<Song>()
                if (song != null) {
                    songs.add(song)
                }
            } else {
                Log.d("FIREBASE", "Song not found for ID: $songId")
            }
        } catch (e: FirebaseFirestoreException) {
            Log.d("FIREBASE", "Error fetching song: ${e.message}")
        }
    }

    return songs
}

suspend fun deleteSongFromUserDisplayList(email: String, song: Song) {
    val db = FirebaseFirestore.getInstance()
    val songId = getSongId(song)

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0].reference

            // Update the lista_a_mostrar array by removing the song ID
            userDoc.update("lista_a_mostrar", FieldValue.arrayRemove(songId)).await()
            Log.d("FIREBASE", "Song removed successfully from lista_a_mostrar")
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error removing song from lista_a_mostrar: ${e.message}")
    }
}


suspend fun deleteSongFromUserDislikeList(email: String, song: Song) {
    val db = FirebaseFirestore.getInstance()
    val songId = getSongId(song)

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0].reference

            // Update the lista_no array by removing the song ID
            userDoc.update("lista_no", FieldValue.arrayRemove(songId)).await()
            Log.d("FIREBASE", "Song removed successfully from lista_no")
        } else {
            Log.d("FIREBASE", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error removing song from lista_no: ${e.message}")
    }
}

suspend fun getUsernameByEmail(email: String): String? {
    val db = FirebaseFirestore.getInstance()
    var username: String? = null

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0]
            username = userDoc.getString("nombre_de_usuario")
            Log.d("GETBYEMAIL", "Username retrieved successfully: $username")
        } else {
            Log.d("GETBYEMAIL", "User not found")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("GETBYEMAIL", "Error retrieving username: ${e.message}")
    }

    return username
}

suspend fun countSongsInUserLikeList(email: String): Int {
    val db = FirebaseFirestore.getInstance()
    var songCount = 0

    try {
        Log.d("SONGCOUNT", "counting songs for email: $email")

        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0]
            val likeList = userDoc.get("lista_si") as? List<*>
            songCount = likeList?.size ?: 0
            Log.d("SONGCOUNT", "Found ${likeList?.size ?: 0} songs in lista_si")
        } else {
            Log.d("SONGCOUNT", "User not found for email: $email")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("SONGCOUNT", "Error counting songs in lista_si: ${e.message}")
    }

    return songCount
}

suspend fun countSongsInUserDislikeList(email: String): Int {
    val db = FirebaseFirestore.getInstance()
    var songCount = 0

    try {
        Log.d("SONGCOUNT", "counting songs for email: $email")

        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0]
            val dislikeList = userDoc.get("lista_no") as? List<*>
            songCount = dislikeList?.size ?: 0
            Log.d("SONGCOUNT", "Found ${dislikeList?.size ?: 0} songs in lista_si")
        } else {
            Log.d("SONGCOUNT", "User not found for email: $email")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("SONGCOUNT", "Error counting songs in lista_no: ${e.message}")
    }

    return songCount
}

suspend fun updateUsernameInFirestore(email: String, newUsername: String) {
    val db = FirebaseFirestore.getInstance()

    try {
        // Get the user's document reference
        val userQuery = db.collection(USERS).whereEqualTo("email", email).get().await()

        if (!userQuery.isEmpty) {
            // Assuming the email is unique and only one document will be returned
            val userDoc = userQuery.documents[0].reference

            // Update the username field
            userDoc.update("nombre_de_usuario", newUsername).await()
            Log.d("FIREBASE", "Username updated successfully in Firestore")
        } else {
            Log.d("FIREBASE", "User not found for email: $email")
        }
    } catch (e: FirebaseFirestoreException) {
        Log.d("FIREBASE", "Error updating username in Firestore: ${e.message}")
    }
}






private fun isSongInList(
    songs: MutableState<List<Song>>, index: Int, toCheckList: Boolean
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
        db.collection("canciones").addSnapshotListener { snapshot, e ->
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
