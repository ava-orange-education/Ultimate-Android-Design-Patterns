sealed class ArticleIntent {
    object LoadArticles : ArticleIntent()
    data class ShowError(val message: String) : ArticleIntent()
}

class ArticleReducer {

    private val _state = MutableStateFlow(ArticleState())
    val state: StateFlow<ArticleState> get() = _state

    fun processIntent(intent: ArticleIntent) {
        when (intent) {
            is ArticleIntent.LoadArticles -> loadArticles()
            is ArticleIntent.ShowError -> showError(intent.message)
        }
    }

    private fun loadArticles() {
        _state.value = _state.value.copy(isLoading = true)

        // Simulates a network call or a data source to obtain data
        val articles = listOf(
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

        _state.value = _state.value.copy(articles = articles, isLoading = false)
    }

    private fun showError(message: String) {
        _state.value = _state.value.copy(error = message, isLoading = false)
    }
}
