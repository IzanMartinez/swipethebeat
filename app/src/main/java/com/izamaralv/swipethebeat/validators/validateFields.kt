package com.izamaralv.swipethebeat.validators

import com.izamaralv.swipethebeat.exceptions.InvalidEmailException
import com.izamaralv.swipethebeat.exceptions.InvalidPasswordException
import com.izamaralv.swipethebeat.exceptions.PasswordMatchErrorException
import com.izamaralv.swipethebeat.exceptions.RequiredFieldsAreEmptyException
import com.izamaralv.swipethebeat.utils.isValidEmail
import com.izamaralv.swipethebeat.utils.isValidPassword

// Funciones de validación
fun validateFields(userEmail: String, userPassword: String, userPasswordAgain: String) {
    if (userEmail.isBlank() || userPassword.isBlank() || userPasswordAgain.isBlank()) {
        throw RequiredFieldsAreEmptyException()
    }
    if (!isValidEmail(email = userEmail)) {
        throw InvalidEmailException()
    }
    if (!isValidPassword(password = userPassword)) {
        throw InvalidPasswordException()
    }
    if (userPassword != userPasswordAgain) {
        throw PasswordMatchErrorException()
    }
}