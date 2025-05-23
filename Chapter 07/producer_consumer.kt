fun main() = runBlocking {
    // Shared queue with a maximum capacity of  10 elements
    val channel = Channel<String>(10)

    // Producer Coroutine
    val producer = launch {
        for (i in 1..10) {
            val item = "Item $i"
            channel.send(item) // Add the element to the channel
            println("Producer: Product $item")
            delay(500) // Simulates a production activity
        }
        channel.close() // Close the channel when the job is finished
    }

    // Consumer Coroutine
    val consumer = launch {
        // Remove and consume the elements from the channel
        for (item in channel) {
            println("Consumer: Consumato $item")
            delay(1000) // Simulates a consumption activity
        }
    }

    producer.join() // Wait for Producer termination
    consumer.join() // Wait for Consumer termination
}
