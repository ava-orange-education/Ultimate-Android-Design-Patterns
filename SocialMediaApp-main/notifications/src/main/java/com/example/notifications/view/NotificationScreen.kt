package com.example.notifications.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notifications.model.Notification
import com.example.notifications.viewmodel.NotificationContract

@Composable
fun NotificationScreen(
    uiState: NotificationUiState,
    contract: NotificationContract? = null
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.padding(16.dp),
            text = "It's your turn: implement the notifications module here!",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.secondary
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationScreenPreview() {
    val notifications = listOf(
        Notification(
            id = 0.toString(),
            message = "Test notification",
            timestamp = System.currentTimeMillis()
        )
    )

    NotificationScreen(NotificationUiState(notifications))
}