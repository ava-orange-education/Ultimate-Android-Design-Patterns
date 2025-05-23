// Interface that defines the execute() method
interface Command {
    fun execute()
}

// Concrete command to add a product to the cart
class AddToCartCommand(private val receiver: CartReceiver) : Command {
    override fun execute() {
        receiver.addItemToCart()
    }
}

// Concrete command to remove a product from the cart
class RemoveFromCartCommand(private val receiver: CartReceiver) : Command {
    override fun execute() {
        receiver.removeItemFromCart()
    }
}

// Receiver
class CartReceiver {
    fun addItemToCart() {
        println("Product added to cart")
    }

    fun removeItemFromCart() {
        println("Product removed from cart")
    }
}

// Invoker
class ButtonInvoker(private var command: Command?) {
    fun setCommand(newCommand: Command) {
        command = newCommand
    }

    fun pressButton() {
        command?.execute()
    }
}

@Composable
fun CommandComposable() {
    // Create the receiver (the cart)
    val cartReceiver = CartReceiver()

    // Create the concrete commands
    val addToCartCommand = AddToCartCommand(cartReceiver)
    val removeFromCartCommand = RemoveFromCartCommand(cartReceiver)

    // Initialize the invoker
    val buttonInvoker = remember { ButtonInvoker(null) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Button to add a product to the cart
        Button(onClick = {
            buttonInvoker.apply {
                setCommand(addToCartCommand)
                pressButton()
            }
        }) {
            Text("Add to cart")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to remove a product from the cart
        Button(onClick = {
            buttonInvoker.apply {
                setCommand(removeFromCartCommand)
                pressButton()
            }
        }) {
            Text("Remove from cart")
        }
    }
}
