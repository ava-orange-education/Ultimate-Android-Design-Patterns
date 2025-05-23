package com.example.notifications.model

class NotificationRepositoryImpl : NotificationRepository {

    override fun getNotifications(): List<Notification> {
        return emptyList()
    }

    override fun markAsRead(notificationId: String) {
        TODO("Not yet implemented")
    }

    override fun deleteNotification(notificationId: String) {
        TODO("Not yet implemented")
    }

    override fun simulateNotification(notification: Notification) {
        TODO("Not yet implemented")
    }
}