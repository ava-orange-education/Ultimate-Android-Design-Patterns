class UserProfile private constructor(
    val name: String,
    val age: Int,
    val email: String,
    val phone: String?,
    val address: String?
) {
    data class Builder(
        var name: String = "",
        var age: Int = 0,
        var email: String = "",
        var phone: String? = null,
        var address: String? = null
    ) {
        fun setName(name: String) = apply { this.name = name }
        fun setAge(age: Int) = apply { this.age = age }
        fun setEmail(email: String) = apply { this.email = email }
        fun setPhone(phone: String?) = apply { this.phone = phone }
        fun setAddress(address: String?) = apply { this.address = address }

        fun build() = UserProfile(name, age, email, phone, address)
    }
}

// Usage
fun createUser(): UserProfile {
    return UserProfile.Builder()
        .setName("Lorenzo")
        .setAge(30)
        .setEmail("lorenzo@example.com")
        .setAddress("123 Main St")
        .build()
}
