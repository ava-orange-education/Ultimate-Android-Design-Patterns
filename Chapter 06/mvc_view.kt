class UserListActivity : AppCompatActivity() {

    private lateinit var controller: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        controller = UserController(this)
        controller.loadUsers()
    }

    fun displayUsers(users: List<User>) {
        // Logic to display the list of the users, for example using a RecyclerView
    }
}