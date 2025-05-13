class ThreadPoolExample {
    // Create a pool with 4 threads
    private val threadPool = Executors.newFixedThreadPool(4)

    fun executeTask(task: Runnable) {
        threadPool.execute(task)
    }

    fun shutdown() {
        threadPool.shutdown()
    }
}

fun main() {
    val threadPoolExample = ThreadPoolExample()

    for (i in 1..10) {
        threadPoolExample.executeTask {
            println("Execution of task $i on thread ${Thread.currentThread().name}")
        }
    }

    threadPoolExample.shutdown()
}
