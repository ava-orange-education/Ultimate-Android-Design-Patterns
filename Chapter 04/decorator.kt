// Interface that defines the common methods between the base component and the decorator
interface ViewDecorator {
    fun display()
}

// Base component that implements the visualization behavior of a simple TextView
class SimpleTextView(private val text: String) : ViewDecorator {
    override fun display() {
        println("TextView shows: $text")
    }
}

// Abstract decorator that implements the interface ViewDecorator and holds a reference to the component
abstract class TextViewDecorator(private val textView: ViewDecorator) : ViewDecorator {
    override fun display() {
        textView.display()
    }
}

// Concrete decorator that adds the login functionality to the TextView
class LoggingTextView(textView: ViewDecorator) : TextViewDecorator(textView) {
    override fun display() {
        println("Logging: User has typed some text.")
        super.display()
    }
}

// Client class that uses the TextView with the decorator to add extra functionalities
class TextViewClient {
    private val textView: ViewDecorator = LoggingTextView(
        SimpleTextView("Hello, Android!")
    )

    fun renderTextView() {
        textView.display()
    }
}

fun main() {
    val textViewClient = TextViewClient()
    textViewClient.renderTextView()

    // Output:
    // Logging: User has typed some text.
    // TextView shows: Hello, Android!
}
