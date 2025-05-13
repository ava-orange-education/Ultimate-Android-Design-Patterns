class AuthManager(private val auth: FirebaseAuth) {

    fun signIn(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun signUp(email: String, password: String, onComplete: (Boolean) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun signOut() {
        auth.signOut()
    }
}

// Usage

val authManager = AuthManager()

authManager.signIn("user@example.com", "password123") { isSuccessful ->
    if (isSuccessful) {
        // Notify the user the message "Authentication successful!"
    } else {
        // Notify the user the message "Authentication failed."
    }
}
