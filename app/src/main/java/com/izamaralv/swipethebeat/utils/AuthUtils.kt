package com.izamaralv.swipethebeat.utils

import android.util.Log
import com.izamaralv.swipethebeat.firebase.auth.Auth
import kotlinx.coroutines.tasks.await

suspend fun createAccount(email: String, password: String): Boolean {
    return try {
        val result = Auth.auth.createUserWithEmailAndPassword(email, password).await()
        addUserDataToCollection(email)
        true
    } catch (e: Exception) {
        Log.e("authError", "Error: ${e.message}", e)
        false
    }
}



suspend fun loginAccount(email: String, password: String): Boolean {
    return try {
        val result = Auth.auth.signInWithEmailAndPassword(email, password).await()
        true
    } catch (e: Exception) {
        Log.e("authError", "Error: ${e.message}", e)
        false
    }
}

fun isValidEmail(email: String): Boolean {
    // email standard validation
    val emailRegex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,})+\$")
    return email.matches(emailRegex)
}

fun isValidPassword(password: String): Boolean {
    // Password should have at least 8 characters, one uppercase letter, and one digit
    val passwordRegex = Regex("^(?=.*[A-Z])(?=.*\\d).{8,}$")
    return password.matches(passwordRegex)
}


