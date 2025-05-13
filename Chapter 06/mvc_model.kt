data class User(val id: Int, val name: String)

class UserRepository {
    fun fetchUsers(): List<User> {
        // Simulation of a network call
        return listOf(User(1, "Mike"), User(2, "John"))
    }
}