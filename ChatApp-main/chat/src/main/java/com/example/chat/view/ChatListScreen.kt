package com.example.chat.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chat.intent.ChatListState
import com.example.chat.model.Conversation
import com.example.chat.model.Message
import com.example.chat.model.MessageDirection
import com.example.core.ConfirmationDialog
import com.example.core.formatDate
import com.example.core.formatTime
import com.example.core.model.Contact
import com.example.core.stringToColor

@Composable
fun ChatListScreen(
    state: ChatListState,
    onChatSelected: (Int) -> Unit,
    onConversationDelete: (Int) -> Unit = {},
    onSimulateMessageReceiving: (String, String) -> Unit
){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(state.conversations.size) { index ->
                val chat = state.conversations[index]
                ChatListItem(
                    chat = chat,
                    onChatSelected = onChatSelected,
                    onConversationDelete = onConversationDelete
                )
            }
        }
        Button(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            onClick = {
                onSimulateMessageReceiving(
                    state.conversations[0].contact.name,
                    state.conversations[0].lastMessage.text
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(text = "Simulate message receiving")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatListItem(
    chat: Conversation,
    onChatSelected: (Int) -> Unit,
    onConversationDelete: (Int) -> Unit = {}
) {
    val context = LocalContext.current

    var showDeleteDialog by remember { mutableStateOf(false) }
    if (showDeleteDialog) {
        ConfirmationDialog (
            onConfirm = {
                onConversationDelete(chat.contact.id)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false }
        )
    }

    Row (
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = { onChatSelected(chat.contact.id) },
                onLongClick = { showDeleteDialog = true }
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val profileImageModifier = Modifier.padding(end = 8.dp)
            .size(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(stringToColor(chat.contact.name))
        if (chat.contact.profilePicture.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(chat.contact.profilePicture)
                    .crossfade(true)
                    .build(),
                placeholder = null,
                contentDescription = null,
                modifier = profileImageModifier
            )
        } else {
            Image(
                imageVector = Icons.Default.PersonOutline,
                contentDescription = null,
                modifier = profileImageModifier
            )
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = chat.contact.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (chat.lastMessage.direction == MessageDirection.SENT) {
                    Image(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 4.dp).alpha(0.5f)
                            .size(12.dp)
                    )
                }
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = chat.lastMessage.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        Column(
            modifier = Modifier.alpha(0.5f).padding(end = 8.dp).width(50.dp),
            horizontalAlignment = Alignment.End
        ) {
            val timestamp = chat.lastMessage.timestamp
            Text(
                text = timestamp.formatDate(),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = timestamp.formatTime(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview(){
    val conversations = mutableListOf<Conversation>()
    listOf("John Doe", "Jane Doe", "Bob Smith", "Alice Johnson").forEach {
        conversations.add(Conversation(
            contact = Contact(name = it),
            lastMessage = Message(
                direction = MessageDirection.SENT,
                text = "Hi, what's something interesting that happened to you today?",
                timestamp = 1234567890,
            )
        ))
    }
    ChatListScreen(
        state = ChatListState(
            conversations = conversations
        ),
        onChatSelected = {},
        onSimulateMessageReceiving = {_, _ -> }
    )
}