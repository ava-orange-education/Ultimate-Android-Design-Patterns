class UserProfile(
    val name: String,
    val age: Int,
    val email: String,
    val phone: String?,
    val address: String?
)

// Usage
fun createUser(): UserProfile {
    return UserProfile(
        name = "Lorenzo",
        age = 30,
        email = "lorenzo@example.com",
        phone = null,
        address = "123 Main St"
    )
}
