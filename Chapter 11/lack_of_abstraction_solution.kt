interface Report {
    fun generate()
}

class PDFReport : Report {
    override fun generate() {
        // Logic to generate PDF
    }
}

class ReportFactory {
    fun createReport(type: String): Report {
        return when (type) {
            "PDF" -> PDFReport()
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }
}
