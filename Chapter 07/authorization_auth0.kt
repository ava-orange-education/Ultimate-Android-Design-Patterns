class OAuthManager(context: Context) {
    private val account = Auth0(context)

    fun login(onSuccess: (String) -> Unit, onFailure: (AuthenticationException) -> Unit) {
        WebAuthProvider.login(account)
            .withScheme("demo") // Configura il tuo scheme
            .start(context as Activity, object : Callback<Credentials, AuthenticationException> {
                override fun onFailure(exception: AuthenticationException) {
                    onFailure(exception)
                }

                override fun onSuccess(credentials: Credentials) {
                    onSuccess(credentials.accessToken)
                }
            }
            )
    }
}
