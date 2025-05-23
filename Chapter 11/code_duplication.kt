fun calculateTotalPrice(items: List<Item>): Double {
    return items.sumOf { it.price }
}

fun calculateTotalWeight(items: List<Item>): Double {
    return items.sumOf { it.weight }
}
