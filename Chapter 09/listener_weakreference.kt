class Listener {
    fun onEvent() = println("Event received!")
}

class EventSource(listener: Listener) {
    private val weakListener = WeakReference(listener)

    fun triggerEvent() {
        weakListener.get()?.onEvent() // Calls the listener if it's still available
    }
}

fun main() {
    val listener = Listener()
    val source = EventSource(listener)

    source.triggerEvent() // Output: Event received!
}
