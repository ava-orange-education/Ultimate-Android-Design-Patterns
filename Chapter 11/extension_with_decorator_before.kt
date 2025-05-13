class DataProcessor {
    fun processData(data: String): String {
        return data.uppercase()
    }

    fun processDataWithLogging(data: String): String {
        println("Processing data: $data")
        return data.uppercase()
    }

    fun processDataWithEncryption(data: String): String {
        // Naive encryption for demonstration purpose
        val encryptedData = StringBuilder()
        for (char in data) {
            encryptedData.append(char + 1)
        }

        return encryptedData
    }
}
