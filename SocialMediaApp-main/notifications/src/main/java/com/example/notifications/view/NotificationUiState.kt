package com.example.notifications.view

import com.example.notifications.model.Notification

data class NotificationUiState(
    val notifications: List<Notification> = emptyList()
)