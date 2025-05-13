interface DataProcessor {
    fun process(data: String): String
}

class BasicDataProcessor : DataProcessor {
    override fun process(data: String): String {
        return data.uppercase()
    }
}

// Decorators
// Each decorator adds a new functionality, delegating the rest to the wrapped object.

class LoggingDecorator(private val processor: DataProcessor) : DataProcessor {
    override fun process(data: String): String {
        println("Logging data: $data")
        return processor.process(data)
    }
}

class EncryptionDecorator(private val processor: DataProcessor) : DataProcessor {
    override fun process(data: String): String {
        val processedData = processor.process(data)
        // Naive encryption for demonstration purpose
        val encryptedData = StringBuilder()
        for (char in processedData) {
            encryptedData.append(char + 1)
        }
        return encryptedData
    }
}

// Usage
fun main() {
    val basicProcessor = BasicDataProcessor()

    val loggingProcessor = LoggingDecorator(basicProcessor)
    val encryptedProcessor = EncryptionDecorator(loggingProcessor)

    val data = "Hello, World"
    val result = encryptedProcessor.process(data)
}
