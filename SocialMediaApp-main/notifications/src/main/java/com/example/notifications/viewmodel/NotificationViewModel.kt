package com.example.notifications.viewmodel

import androidx.lifecycle.ViewModel
import com.example.notifications.model.Notification
import com.example.notifications.model.NotificationRepository
import com.example.notifications.view.NotificationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    notificationRepository: NotificationRepository
) : NotificationContract, ViewModel(){

    private val _uiState = MutableStateFlow< NotificationUiState>(NotificationUiState())
    val uiState: StateFlow<NotificationUiState> = _uiState.asStateFlow()

    init {
        _uiState.update {
            currentState -> currentState.copy(
                notifications = notificationRepository.getNotifications()
            )
        }
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