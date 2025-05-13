fun main() = runBlocking {
    val customDispatcher = Executors.newFixedThreadPool(4).asCoroutineDispatcher()

    repeat(10) { index ->
        launch(customDispatcher) {
            println("Execution of task $index on thread thread ${Thread.currentThread().name}")
        }
    }

    (customDispatcher.executor as ExecutorService).shutdown()
}
