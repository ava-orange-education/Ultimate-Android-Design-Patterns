class ArticlePresenter(private val view: ArticleView, private val repository: ArticleRepository) {

    fun loadArticles() {
        val articles = repository.getArticles()
        if (articles.isNotEmpty()) {
            view.showArticles(articles)
        } else {
            view.showError("No article found")
        }
    }

    fun addArticle(title: String) {
        val newArticle = Article(repository.getArticles().size + 1, title)
        repository.addArticle(newArticle)
        loadArticles()
    }
}
