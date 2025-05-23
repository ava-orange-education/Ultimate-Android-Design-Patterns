data class Article(
    val title: String,
    val content: String
)

// DataSource to obtain data from a simulated API
object ArticleDataSource {
    // Simulate a network call to fetch articles from an API
    fun fetchArticles(): List<Article> = listOf(
        Article(
            title = "How to Train Your Coffee Mug",
            content = "Sit. Stay. Hold my brew."
        ),
        Article(
            title = "Secrets of the Lazy Cat",
            content = "Sleep. Eat. Repeat."
        ),
        Article(
            title = "Guide to Napping Anywhere",
            content = "Step 1: Find a cozy spot."
        )
    )
}

// Repository that uses DataSource to fetch data
class ArticleRepository(private val dataSource: ArticleDataSource) {
    fun getArticles(): List<Article> = dataSource.fetchArticles()
}
