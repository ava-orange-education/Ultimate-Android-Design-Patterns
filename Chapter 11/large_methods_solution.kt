interface OrderProcessor {
    fun process(order: Order)
}

class ValidationProcessor : OrderProcessor {
    override fun process(order: Order) {
        // Logic for validation
    }
}

class DiscountProcessor : OrderProcessor {
    override fun process(order: Order) {
        // Logic to apply discounts
    }
}
