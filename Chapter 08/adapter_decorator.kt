// Adapter

interface Logger {
    fun log(message: String)
}

class ThirdPartyLogger {
    fun logToConsole(msg: String) {
        println("Third-party log: $msg")
    }
}

class LoggerAdapter(private val thirdPartyLogger: ThirdPartyLogger) : Logger {
    override fun log(message: String) {
        thirdPartyLogger.logToConsole(message)
    }
}

// Decorator

class FileLoggerDecorator(private val logger: Logger) : Logger {
    override fun log(message: String) {
        logger.log(message)
        saveToFile(message)
    }

    private fun saveToFile(message: String) {
        println("Saved to file: $message")
    }
}

// Usage

val thirdPartyLogger = ThirdPartyLogger()
val adapter = LoggerAdapter(thirdPartyLogger)
val decoratedLogger = FileLoggerDecorator(adapter)

decoratedLogger.log("Log message")
// Output:
// Third-party log: Log message
// Saved to file: Log message
