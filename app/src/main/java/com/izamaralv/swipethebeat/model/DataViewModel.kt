package com.izamaralv.swipethebeat.model

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.izamaralv.swipethebeat.data.entities.Song
import com.izamaralv.swipethebeat.utils.getDataFromFirestore
import kotlinx.coroutines.launch

class DataViewModel : ViewModel() {
    private val _songs = mutableStateOf<List<Song>>(emptyList())
    private val _song = mutableStateOf<Song?>(null)
    val songs: State<List<Song>> = _songs
    val song: State<Song?> = _song
    private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)
    private val _currentUser = MutableLiveData<FirebaseUser?>(null)

    init {
        getData()

    }
    private fun getData() {
        viewModelScope.launch {
            _songs.value = getDataFromFirestore().value
        }
    }


    //Inicio de sesión con mail y contraseña
    fun signInWithEmailPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {

                // Validación básica (reemplace con verificaciones más robustas)
                if (email.isEmpty() || password.length < 6) {
                    // Mostrar mensaje de error al usuario
                    return@launch
                }



                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("Lego", "signInWithEmailPassword bloqueado!!")
                        home()
                        _currentUser.value = auth.currentUser // Actualizar el usuario actual

                    } else {
                        Log.d("Lego", "signInWithEmailPassword ${task.result.toString()}")
                    }
                }
            } catch (ex: Exception) {
                Log.d("Lego", "signInWithEmailPassword ${ex.message}")


            }

        }
    fun logOut() {
        FirebaseAuth.getInstance().signOut()
    }

}
