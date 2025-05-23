val executor = Executors.newSingleThreadExecutor()

fun runTask() {
    executor.execute {
        // Simulate a background task
        println("Running a quick background task")
    }
}
