interface LegacyInterface {
    fun legacyMethod()
}

// Implementation of Target
class NewSystem {
    fun newSystemMethod() {
        println("Method of the new system in execution")
    }
}

// Adapter class that implements the Legacy interface and adapts to the NewSystem
class Adapter(private val newSystem: NewSystem) : LegacyInterface {
    override fun legacyMethod() {
        newSystem.newSystemMethod()
    }
}

// Implementation of Client
class Client(private val legacyInterface: LegacyInterface) {
    fun execute() {
        legacyInterface.legacyMethod()
    }
}

fun main() {
    val newSystem = NewSystem()
    val adapter = Adapter(newSystem)
    val client = Client(adapter)

    // Prints "Method of the new system in execution"
    client.execute()
}

// Test 1
// @Test
fun testAdapter() {
    val newSystem = NewSystem()
    val adapter = Adapter(newSystem)
    val client = Client(adapter)

    client.execute()
}

// Test 2
// @Test
fun testAdapterCompatibility() {
    val newSystem = NewSystem()
    val adapter = Adapter(newSystem)

    assert(adapter is LegacyInterface)
}