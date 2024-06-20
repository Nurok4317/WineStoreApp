package com.example.winestoreapp.model
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun checkIfAdmin(userId: String, onResult: (Boolean) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    db.collection("users").document(userId).get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val role = document.getString("role")
                onResult(role == "admin")
            } else {
                onResult(false)
            }
        }
        .addOnFailureListener {
            onResult(false)
        }
}
