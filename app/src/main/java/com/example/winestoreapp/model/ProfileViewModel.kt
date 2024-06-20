package com.example.winestoreapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.winestoreapp.data.UserProfile
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ProfileViewModel : ViewModel() {
    private val _profile = MutableStateFlow<UserProfile?>(null)
    val profile: StateFlow<UserProfile?> = _profile

    private val _updateMessage = MutableStateFlow<String?>(null)
    val updateMessage: StateFlow<String?> = _updateMessage

    fun loadUserProfile(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                _profile.value = document.toObject(UserProfile::class.java)
            }
            .addOnFailureListener {
                _profile.value = null // handle error case
            }
    }

    fun updateUserProfile(profile: UserProfile) {
        _profile.value = profile

        // Save profile to Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("users").document(profile.userId).set(profile)
            .addOnSuccessListener {
                _updateMessage.value = "Profile successfully updated"
            }
            .addOnFailureListener {
                _updateMessage.value = "Failed to update profile"
            }
    }

    fun clearUpdateMessage() {
        _updateMessage.value = null
    }
}
