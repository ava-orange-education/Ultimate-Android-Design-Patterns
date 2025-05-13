val job = Job()
val coroutineScope = CoroutineScope(Dispatchers.Main + job)

fun onUserInput(input: String) {
    coroutineScope.launch {
        delay(300) // Debounce: delay the execution by 300ms
        performSearch(input)
    }
}
