@Composable
fun UserScreen() {
    var users by remember { mutableStateOf(emptyList<User>()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading = true
        try {
            // Business logic inside UI
            users = fetchUsersFromNetwork()
        } catch (e: Exception) {
            // Error handling
        } finally {
            isLoading = false
        }
    }

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

data class User(val name: String)

suspend fun fetchUsersFromNetwork(): List<User> {
    // Business logic: simulation of a network request
    return listOf(User("Alice"), User("Bob"))
}
