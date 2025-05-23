class UserController(private val view: UserListActivity) {

    private val repository = UserRepository()

    fun loadUsers() {
        val users = repository.fetchUsers()
        view.displayUsers(users)
    }
}
