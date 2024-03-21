package com.example.bankapp.ui.theme.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.model.ProfileInformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    private val _userInformation = MutableLiveData<ProfileInformation>()
    val userInformation: LiveData<ProfileInformation> = _userInformation

    init {
        showPersonalInformation()
    }

    private fun showPersonalInformation() {
        val userId = auth.currentUser?.uid
        val docRef = db.collection("users").document(userId.toString())
        viewModelScope.launch {
            try {
                docRef.get()
                    .addOnSuccessListener { document ->
                        val userInformation = document.toObject<ProfileInformation>()
                        if (userInformation != null) {
                            _userInformation.value = document.toObject<ProfileInformation>()
                        }
                    }
            } catch (e: Exception) {
                println("error de data ${e.message}")
            }
        }
    }
}
