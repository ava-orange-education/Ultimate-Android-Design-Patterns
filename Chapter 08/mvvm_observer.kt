// ViewModel
class TaskViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<String>>(emptyList())
    val tasks: StateFlow<List<String>> = _tasks

    fun addTask(task: String) {
        val updatedTasks = _tasks.value.toMutableList().apply { add(task) }
        _tasks.value = updatedTasks
    }
}

// View with Jetpack Compose
@Composable
fun TaskListScreen(viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()

    Column {
        tasks.forEach { task ->
            Text(text = task)
        }

        Button(onClick = { viewModel.addTask("New Task") }) {
            Text("Add Task")
        }
    }
}
