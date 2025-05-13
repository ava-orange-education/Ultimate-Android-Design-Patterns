// Data type
data class User(
    val id: Int,
    val name: String,
    val email: String
)

// Remote data source (API)
interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>
}

// Local data source (Database)
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
}

@Entity
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val email: String
)

// Repository
class UserRepository(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    fun getUsers(): Flow<List<User>> = flow {
        // Try to fetch data from the local database
        val localUsers = userDao.getAllUsers()
        if (localUsers.isNotEmpty()) {
            emit(localUsers.map { it.toUser() })
        }

        // Fetch data from API
        try {
            val remoteUsers = apiService.getUsers()
            emit(remoteUsers)

            // Save data on the local database
            userDao.insertUsers(remoteUsers.map { it.toUserEntity() })
        } catch (e: Exception) {
            // In case of error, return only local data
            emit(localUsers.map { it.toUser() })
        }
    }

    // Mappers to convert UserEntity to User and vice versa
    private fun UserEntity.asUser() = User(id, name, email)
    private fun User.asUserEntity() = UserEntity(id, name, email)
}
