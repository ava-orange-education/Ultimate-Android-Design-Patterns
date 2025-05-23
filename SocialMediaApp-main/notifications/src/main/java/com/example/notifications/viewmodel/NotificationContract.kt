package com.example.notifications.viewmodel

import com.example.notifications.model.Notification

interface NotificationContract {

    fun markAsRead(notificationId: String)

    fun deleteNotification(notificationId: String)

    fun simulateNotification(notification: Notification)
}