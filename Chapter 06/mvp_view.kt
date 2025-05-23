interface ArticleView {
    fun showArticles(articles: List<Article>)
    fun showError(message: String)
}

class ArticleListActivity : AppCompatActivity(), ArticleView {

    private lateinit var presenter: ArticlePresenter
    private lateinit var repository: ArticleRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        repository = ArticleRepository()
        presenter = ArticlePresenter(this, repository)

        // Loads the articles
        presenter.loadArticles()

        // Button to add a new article
        findViewById<Button>(R.id.addArticleButton).setOnClickListener {
            presenter.addArticle("New Article ${repository.getArticles().size + 1}")
        }
    }

    override fun showArticles(articles: List<Article>) {
        // Shows the list of the articles
        articles.forEach { article ->
            Log.d("ArticleListActivity", "ID: ${article.id}, Title: ${article.title}")
        }
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
