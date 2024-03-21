package com.example.bankapp.ui.theme.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankapp.data.model.UserMovement
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth

    private val _allMovements = MutableLiveData<UserMovement>()
    val allMovements: LiveData<UserMovement> = _allMovements

    init {
        showMovementsContent()
    }

    private fun showMovementsContent() {
        val userId = auth.currentUser?.uid
        val docRef = db.collection("movements").document(userId.toString())
        viewModelScope.launch {
            try {
                docRef.get()
                    .addOnSuccessListener { document ->
                        val movements = document.toObject<UserMovement>()
                        if (movements != null) {
                            _allMovements.value = document.toObject<UserMovement>()
                        } else {
                            println("EStos son los datos : ${movements?.movements}")
                            println("EStos son los datos en document : ${document.data}")
                        }
                    }
            } catch (e: Exception) {
                println("error de data ${e.message}")
            }
        }
    }
}
