package com.example.bankapp.ui.theme.register

import android.net.Uri
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bankapp.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.*
import java.io.File


class RegisterViewModel() : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    val showErrorDialog = mutableStateOf(false)
    val showConfirmDialog = mutableStateOf(false)
    val storage = Firebase.storage.reference
    val isTakePhoto = mutableStateOf(false)

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _lastName = MutableLiveData<String>()
    val lastName: LiveData<String> = _lastName

    private val _isEnabled = MutableLiveData<Boolean>()
    val isEnabled: LiveData<Boolean> = _isEnabled

    fun onCreateAccountChanged(
        email: String,
        password: String,
        name: String,
        lastName: String
    ) {
        _email.value = email
        _password.value = password
        _name.value = name
        _lastName.value = lastName
        _isEnabled.value = enableCreateAccount(email, password)
    }

    private fun enableCreateAccount(email: String, password: String) =
        Patterns.EMAIL_ADDRESS.matcher(email).matches() && password.length > 5

    fun onCreateAccount(
        email: String,
        password: String,
        name: String,
        lastName: String,
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    createUser(name, lastName)
                } else {
                    println("Create user: ${it.result}")
                }
            }
    }

    private fun createUser(
        name: String,
        lastName: String,
    ) {
        val userId = auth.currentUser?.uid
        val user =
            User(
                userId = userId.toString(),
                name = name,
                lastName = lastName
            ).toMap()

        FirebaseFirestore.getInstance().collection("users").document(userId.toString())
            .set(user)
            .addOnSuccessListener {
                showConfirmDialog.value = true
            }
            .addOnFailureListener {
                showErrorDialog.value = true
            }
    }

    fun uploadPictureId(path: String) {
        val file: Uri = Uri.fromFile(File(path))
        val userIdRef = storage.child("images/${file.lastPathSegment}")
        userIdRef.putFile(file)
            .addOnSuccessListener { taskSnapshot ->
                Log.d("File upload Success", taskSnapshot.toString())
            }
            .addOnFailureListener {
                Log.d("File upload Error", it.message.toString())
            }
    }
}
