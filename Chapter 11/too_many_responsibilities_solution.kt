class OrderManager(
    private val orderRepository: OrderRepository,
    private val notificationService: NotificationService,
    private val invoiceService: InvoiceService
) {
    fun placeOrder(order: Order) {
        orderRepository.save(order)
        notificationService.notify(order)
        invoiceService.generate(order)
    }
}

