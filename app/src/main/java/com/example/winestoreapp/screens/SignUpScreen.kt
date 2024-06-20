import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

fun registerUser(email: String, password: String, isAdmin: Boolean) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                if (user != null) {
                    val userRole = if (isAdmin) "admin" else "user"
                    val userData = hashMapOf(
                        "email" to email,
                        "role" to userRole
                    )

                    db.collection("users").document(user.uid).set(userData)
                        .addOnSuccessListener {
                            // User registered and role assigned
                        }
                        .addOnFailureListener {
                            // Handle failure
                        }
                }
            } else {
                // Handle registration failure
            }
        }
}
