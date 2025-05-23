package com.example.user_profile.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.authentication.viewmodel.AuthContract
import com.example.core.loremIpsum
import com.example.core.model.UserProfile
import com.example.user_profile.viewmodel.ProfileContract

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    authContract: AuthContract? = null,
    profileContract: ProfileContract? = null
) {
    val context = LocalContext.current
    val editMode = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        profileContract?.fetchUserInfo()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uiState.userProfile.profileImageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = null,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(128.dp)
                        .clip(RoundedCornerShape(64.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                )

                Text(
                    text = "@${uiState.userProfile.username}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                EditableText(
                    text = uiState.userProfile.displayName,
                    editMode = editMode.value,
                    onValueChange = {
                        profileContract?.updateDisplayName(it)
                    },
                    style = MaterialTheme.typography.displaySmall.copy(
                        textAlign = TextAlign.Center
                    )
                )
                EditableText(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    editMode = editMode.value,
                    text = uiState.userProfile.bio,
                    style = TextStyle.Default.copy(
                        fontSize = 20.sp
                    ),
                    onValueChange = {
                        profileContract?.updateBio(it)
                    },
                )
                Spacer(modifier = Modifier.height(80.dp))
                Button(onClick = { authContract?.logout(uiState.userProfile.username) }) {
                    Text("Logout")
                }
            }
            Image(
                imageVector = if (editMode.value) Icons.Default.Check else Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .align(Alignment.TopEnd)
                    .clickable(
                        onClick = {
                            editMode.value = !editMode.value
                        }
                    ).testTag(if (editMode.value) "Apply" else "Edit"),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }
}

@Composable
fun EditableText(
    modifier: Modifier = Modifier,
    text: String,
    editMode: Boolean,
    onValueChange: (String) -> Unit,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified
) {
    if (editMode) {
        TextField(
            modifier = modifier,
            value = text,
            onValueChange = onValueChange,
            textStyle = style
        )
    } else {
        Text(
            modifier = modifier,
            text = text,
            style = style,
            color = color
        )
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    Surface {
        ProfileScreen(
            uiState = ProfileUiState(
                UserProfile(
                    username = "john_doe",
                    displayName = "John Doe",
                    email = "john.doe@example.com",
                    bio = loremIpsum,
                    profileImageUrl = "https://example.com/profile_image.jpg"
                )
            )
        )
    }
}