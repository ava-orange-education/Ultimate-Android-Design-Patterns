class UploadWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        // Simulate file upload
        println("Uploading file...")
        return Result.success()
    }
}

// Enqueue WorkManager task
fun scheduleUpload(context: Context) {
    val uploadRequest = OneTimeWorkRequestBuilder<UploadWorker>().build()
    WorkManager.getInstance(context).enqueue(uploadRequest)
}
