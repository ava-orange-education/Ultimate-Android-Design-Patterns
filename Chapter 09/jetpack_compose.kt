@Composable
fun UserListScreen(users: List<User>) {
    LazyColumn {
        items(users) { user ->
            Text(text = user.name)
        }
    }
}
