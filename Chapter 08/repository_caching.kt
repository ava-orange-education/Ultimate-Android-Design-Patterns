class ProductRepository(
    private val memoryCache: MemoryCache<String, List<Product>>,
    private val apiService: ApiService,
    private val database: ProductDatabase
) {

    suspend fun getProducts(): List<Product> {
        // Fetch from memory
        memoryCache.get("products")?.let {
            return it
        }

        // Fetch from local database
        val localProducts = database.productDao().getAllProducts()
        if (localProducts.isNotEmpty()) {
            memoryCache.put("products", localProducts)
            return localProducts
        }

        // Fetch from network
        val remoteProducts = apiService.getProducts()
        database.productDao().insertAll(remoteProducts)
        memoryCache.put("products", remoteProducts)
        return remoteProducts
    }
}
