package com.example.chatapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chat.intent.ChatState
import com.example.chatapp.navigation.AppScreen
import com.example.chatapp.ui.theme.ChatAppTheme
import com.example.chatapp.R
import com.example.core.stringToColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    navController: NavController? = null,
    currentDestination: String?,
    chatState: ChatState
) {
    val context = LocalContext.current
    val isChatScreen = currentDestination?.startsWith(AppScreen.ChatScreen.route) == true
    val title = currentDestination?.let {
        when (it) {
            AppScreen.ChatListScreen.route -> stringResource(id = R.string.chats)
            AppScreen.ContactListScreen.route -> stringResource(id = R.string.contacts)
            else -> if (isChatScreen) {
                chatState.contact.name
            } else ""
        }
    } ?: ""
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isChatScreen) {
                    Image(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier.padding(end = 8.dp)
                            .clickable(
                                onClick = {
                                    navController?.popBackStack(
                                        route = AppScreen.ChatListScreen.route,
                                        inclusive = false
                                    )
                                }
                            )
                    )
                    val profileImageModifier = Modifier.padding(end = 8.dp)
                        .size(48.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(stringToColor(chatState.contact.name))
                    if (chatState.contact.profilePicture.isNotEmpty()) {
                        AsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(chatState.contact.profilePicture)
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
                }
                Text(
                    text = title
                )
            }
        }
    )
}

@Preview
@Composable
fun MyTopAppBarPreview(){
    ChatAppTheme {
        Surface {
            MyTopAppBar(
                currentDestination = AppScreen.ChatScreen.route,
                chatState = ChatState()
            )
        }
    }
}