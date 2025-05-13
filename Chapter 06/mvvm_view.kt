@Composable
fun Article(article: Article) {
    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text = article.title)
        Text(text = article.content, color = Color.Gray)
    }
}

@Composable
fun ArticleListScreen(viewModel: ArticleViewModel = viewModel()) {
    val articles: List<Article> = viewModel.articles.collectAsState(emptyList()).value

    Column(
        modifier = Modifier.padding(16.dp),
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "Articles",
            style = MaterialTheme.typography.headlineMedium
        )
        articles.forEach {
            Article(it)
        }
    }
}
