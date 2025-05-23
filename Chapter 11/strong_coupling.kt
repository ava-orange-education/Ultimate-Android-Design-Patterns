class ReportGenerator {
    private val database = DatabaseConnection()

    fun generateReport() {
        database.query("SELECT * FROM reports")
    }
}
