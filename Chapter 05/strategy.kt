// Interface that all the concrete strategies must implement
@UmlInterface("Strategy")
abstract class ShippingStrategy {

    abstract val cost: Double

    // Every class that implements ShippingStrategy must initialize "cost"
    fun calculateCost(weight: Double): Double {
        return weight * cost
    }
}

@UmlClass("ConcreteStrategy")
class StandardShipping : ShippingStrategy() {

    override val cost = 5.0

}

@UmlClass("ConcreteStrategy")
class PremiumShipping : ShippingStrategy() {

    override val cost = 10.0

}

@UmlClass("ConcreteStrategy")
class InternationalShipping : ShippingStrategy() {

    override val cost = 20.0

}

@UmlClass("Context")
class Order(private var strategy: ShippingStrategy) {

    fun calculateShippingCost(weight: Double): Double {
        return strategy.calculateCost(weight)
    }

}

@Composable
fun ShippingCalculator() {
    var weight by remember { mutableDoubleStateOf(1.0) }
    var shippingStrategy: ShippingStrategy by remember {
        mutableStateOf(StandardShipping())
    }
    val order = Order(shippingStrategy)

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = weight.toString(),
            onValueChange = { weight = it.toDoubleOrNull() ?: 1.0 },
            label = { Text("Package weight (kg)") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { shippingStrategy = StandardShipping() }) {
            Text("Standard Shipping")
        }
        Button(onClick = { shippingStrategy = PremiumShipping() }) {
            Text("Premium Shipping")
        }
        Button(onClick = { shippingStrategy = InternationalShipping() }) {
            Text("International Shipping")
        }
        Spacer(modifier = Modifier.height(16.dp))

        val shippingCost = order.calculateShippingCost(weight)
        val eurFormat = NumberFormat.getCurrencyInstance().apply {
            maximumFractionDigits = 2
            currency = Currency.getInstance("EUR")
        }
        Text("Shipping cost: ${eurFormat.format(shippingCost)}")
    }
}
