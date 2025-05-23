class Singleton {
    companion object {
        private var instance: Singleton? = null
        fun getInstance(): Singleton {
            if (instance == null){
                instance = Singleton()
            }
          return instance!!
        }
    }
}

fun main(){
    val instance1 = Singleton.getInstance()
    val instance2 = Singleton.getInstance()
    // Here instance1 and instance2 point to the same object
}
