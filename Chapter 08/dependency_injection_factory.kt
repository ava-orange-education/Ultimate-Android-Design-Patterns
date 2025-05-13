// Factory

interface NetworkClient {
    fun connect()
}

class RetrofitClient : NetworkClient {
    override fun connect() {
        println("Connecting via Retrofit")
    }
}

class WebSocketClient : NetworkClient {
    override fun connect() {
        println("Connecting via WebSocket")
    }
}

class NetworkClientFactory {
    fun createClient(type: String): NetworkClient {
        return when (type) {
            "rest" -> RetrofitClient()
            "websocket" -> WebSocketClient()
            else -> throw IllegalArgumentException("Unsupported client type")
        }
    }
}

// Dependency Injection

class NetworkModule(private val factory: NetworkClientFactory) {
    fun provideNetworkClient(type: String): NetworkClient {
        return factory.createClient(type)
    }
}


// Usage

val factory = NetworkClientFactory()
val networkModule = NetworkModule(factory)

val restClient = networkModule.provideNetworkClient("rest")
restClient.connect() // Output: "Connecting via Retrofit”

val websocketClient = networkModule.provideNetworkClient("websocket")
websocketClient.connect() // Output: "Connecting via WebSocket”
