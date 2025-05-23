package com.example.chat.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chat.intent.ChatState
import com.example.chat.model.Message
import com.example.chat.model.MessageDirection
import com.example.core.ConfirmationDialog
import com.example.core.format
import com.example.core.model.Contact

@Composable
fun MessageView(
    message: Message,
    onMessageDelete: (Int) -> Unit = {}
){
    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        ConfirmationDialog (
            onConfirm = {
                onMessageDelete(message.id)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().pointerInput(Unit){
                detectTapGestures(
                    onLongPress = {
                        showDeleteDialog = true
                    }
                )
            },
            horizontalArrangement = if (message.direction == MessageDirection.SENT)
                Arrangement.End
            else Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        if (message.direction == MessageDirection.SENT)
                            MaterialTheme.colorScheme.primaryContainer
                        else MaterialTheme.colorScheme.tertiaryContainer
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (message.direction == MessageDirection.SENT)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else MaterialTheme.colorScheme.onTertiaryContainer,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        Text(
            text = message.timestamp.format(),
            modifier = Modifier
                .align(if (message.direction == MessageDirection.SENT) Alignment.End
                    else Alignment.Start)
                .alpha(0.4f),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ChatScreen(
    state: ChatState,
    onMessageSent: (Message) -> Unit,
    onMessageDelete: (Int) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().padding(8.dp).imePadding()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.messages.size) { index ->
                val message = state.messages[index]
                MessageView(
                    message = message,
                    onMessageDelete = onMessageDelete
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        var inputText by remember { mutableStateOf("") }
        Row(modifier = Modifier.fillMaxWidth().height(50.dp)) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        val message = Message(
                            text = inputText,
                            timestamp = System.currentTimeMillis()
                        )
                        onMessageSent(message)
                        inputText = ""
                    }
                },
                modifier = Modifier.padding(start = 8.dp).fillMaxHeight(1f)
            ) {
                Image(
                    imageVector = Icons.AutoMirrored.Default.Send,
                    contentDescription = "Send",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview(){
    ChatScreen(
        state = ChatState(
            contact = Contact(name = "Jhon Doe"),
            messages = listOf(
                Message(
                    direction = MessageDirection.SENT,
                    text = "Hello! How are you?",
                    timestamp = 1234567890,
                ),
                Message(
                    direction = MessageDirection.RECEIVED,
                    text = "I'm fine, thank you",
                    timestamp = 1234567890,
                )
            )
        ),
        onMessageSent = {}
    )
}