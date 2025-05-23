@Composable
fun ArticleListScreen(reducer: ArticleReducer) {
    val state = reducer.state.collectAsState().value

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Build the UI based on the current state
        when {
            state.isLoading -> CircularProgressIndicator()
            state.error != null -> ErrorView(state.error) {
                reducer.processIntent(ArticleIntent.LoadArticles) // Retry Intent
            }

            else -> ArticleList(articles = state.articles)
        }

        // Button to trigger the intent
        Button(
            onClick = { reducer.processIntent(ArticleIntent.LoadArticles) }
        ) {
            Text(text = "Load Articles")
        }
    }
}

@Composable
fun ArticleView(article: Article) {
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = article.title)
        Text(text = article.content, color = Color.Gray)
    }
}

@Composable
fun ArticleList(articles: List<Article>) {
    // Shows the list of the articles
    Column {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Articles",
            style = MaterialTheme.typography.headlineMedium
        )
        articles.forEach {
            ArticleView(it)
        }
    }
}

@Composable
fun ErrorView(error: String, onRetry: () -> Unit = {}) {
    // Shows the error and allows to perform a retry
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Error: $error",
            color = MaterialTheme.colorScheme.error
        )
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}