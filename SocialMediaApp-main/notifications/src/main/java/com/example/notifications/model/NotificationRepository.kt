package com.example.notifications.model

interface NotificationRepository {

    fun getNotifications(): List<Notification>

    fun markAsRead(notificationId: String)

    fun deleteNotification(notificationId: String)

    fun simulateNotification(notification: Notification)

}