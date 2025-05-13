class MySingleton private constructor(private val context: Context) {
    companion object {
        private var instance: MySingleton? = null
        fun getInstance(context: Context): MySingleton {
            if (instance == null) {
                // Don’t do
                // instance = MySingleton(context)
                // Do
                instance = MySingleton(context.applicationContext)
            }
            return instance!!
        }
    }
}
