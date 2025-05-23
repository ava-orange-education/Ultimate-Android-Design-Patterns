fun processOrder(order: Order) {
    validateOrder(order)
    calculateTotal(order)
    applyDiscount(order)
    saveOrder(order)
    sendNotification(order)
}
