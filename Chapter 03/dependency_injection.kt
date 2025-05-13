interface Service {
    fun performAction()
}

class ServiceImpl : Service {
    override fun performAction() {
        // Concrete implementation
    }
}

class Client(private val service: Service) {
    fun execute() {
        service.performAction()
    }
}

class Injector {
    fun inject(): Client {
        val service = ServiceImpl()
        return Client(service)
    }
}

fun main() {
    val injector = Injector()
    val client = injector.inject()
    client.execute()
    // Here ServiceImpl.performAction() has been called
}

// Test 1
// @Test
fun testDependencyInjection() {
    val injector = Injector()
    val client = injector.inject()
    assert(client.execute() == Unit)
}

// Test 2
class MockService : Service {
    override fun performAction() {
        //
    }
}

// @Test
fun testDependencyReplacement() {
    val client = Client(MockService())
    assert(client.execute() == Unit)
}
