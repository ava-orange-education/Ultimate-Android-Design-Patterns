// Command pattern interface
interface Command {
    suspend fun execute()
}

// Class to share data between commands
class CommandContext {
    var data: List<String>? = null
}

// Implementation of the commands
class FetchDataCommand(private val context: CommandContext) : Command {

    override suspend fun execute() {
        context.data = fetchDataFromNetwork()
        println("Data loaded: ${context.data}")
    }

    private suspend fun fetchDataFromNetwork(): List<String> {
        // Simulate a network request
        delay(2000)
        return listOf("item1", "item2")
    }
}

class SaveDataCommand(private val context: CommandContext) : Command {

    override suspend fun execute() {
        val data = context.data ?: throw IllegalStateException("No data available")
        saveDataToDatabase(data)
        println("Data saved on database")
    }

    private suspend fun saveDataToDatabase(data: List<String>) {
        // Simulate logic to save to database
        delay(1000)
    }
}

class SendNotificationCommand(private val message: String) : Command {

    override suspend fun execute() {
        sendNotification(message)
        println("Notification sent: $message")
    }

    private suspend fun sendNotification(message: String) {
        // Simulate logic to send a notification
        delay(500)
    }

}

// Command Manager
class CommandExecutor(context1: CommandContext) {
    private val commands = mutableListOf<Command>()

    fun addCommand(command: Command) {
        commands.add(command)
    }

    suspend fun executeAll() {
        for (command in commands) {
            command.execute()
        }
    }
}

// Usage
fun main() = runBlocking {
    val context = CommandContext()
    val executor = CommandExecutor(context)

    executor.addCommand(FetchDataCommand(context))
    executor.addCommand(SaveDataCommand(context))
    executor.addCommand(SendNotificationCommand("Processing complete"))

    executor.executeAll()
}
