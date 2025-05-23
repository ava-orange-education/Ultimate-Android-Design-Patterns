interface CalculationStrategy {
    fun calculate(items: List<Item>): Double
}

class PriceCalculation : CalculationStrategy {
    override fun calculate(items: List<Item>): Double = items.sumOf { it.price }
}

class WeightCalculation : CalculationStrategy {
    override fun calculate(items: List<Item>): Double = items.sumOf { it.weight }
}
