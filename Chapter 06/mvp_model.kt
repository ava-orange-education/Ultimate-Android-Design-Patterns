data class Article(val id: Int, val title: String)

class ArticleRepository {
    private val articles = mutableListOf<Article>(
        Article(1, "Article 1"),
        Article(2, "Article 2")
    )

    fun getArticles(): List<Article> {
        return articles
    }

    fun addArticle(article: Article) {
        articles.add(article)
    }
}
