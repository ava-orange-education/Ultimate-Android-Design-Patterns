class ArticleViewModel : ViewModel() {
    private val repository = ArticleRepository(ArticleDataSource)

    // StateFlow that exposes the list of articles
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> get() = _articles

    init {
        fetchArticles()
    }

    private fun fetchArticles() {
        // Obtains the data from the Repository and sets it in the StateFlow
        _articles.value = repository.getArticles()
    }
}