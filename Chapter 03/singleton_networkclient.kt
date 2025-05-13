// Implementation of Singleton
object NetworkClient {

    var requestCount: Int = 0

    fun sendNetworkRequest(): Int {
        // Perform network request
        requestCount++
        return 0
    }

}

//@Test
fun singletonTest() {
    // Get the first instance and update a property
    val instance1 = NetworkClient
    instance1.requestCount++

    // Get another instance and check if it reflects the update
    val instance2 = NetworkClient
    assert(instance2.requestCount == 1)

    // Modify again and check consistency
    instance2.requestCount += 2
    assert(instance1.requestCount == 5)
}