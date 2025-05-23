// Model

data class User(val name: String)

class UserRepository {
    suspend fun fetchUsers(): List<User> {
        // Simulation of a network request
        return listOf(User("Alice"), User("Bob"))
    }
}

// ViewModel

class UserViewModel(private val repository: UserRepository) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        repository.fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _users.value = repository.fetchUsers()
            } catch (e: Exception) {
                // Error handling
                _users.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

// View

@Composable
fun UserScreen(viewModel: UserViewModel = viewModel()) {
    val users by viewModel.users.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        CircularProgressIndicator()
    } else {
        LazyColumn {
            items(users) { user ->
                Text(text = user.name)
            }
        }
    }
}
