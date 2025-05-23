package com.example.post_creation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.ErrorOutline
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.loremIpsum
import com.example.core.model.Post
import com.example.core.model.UserProfile
import com.example.post_creation.viewmodel.PostCreationContract

@Composable
fun PostCreationScreen(
    uiState: PostCreationUiState,
    contract: PostCreationContract? = null,
){
    LaunchedEffect(Unit) {
        contract?.fetchUserInfo()
    }

    when(uiState.status){
        PostStatus.WRITING -> PostStatusWriting(
            uiState = uiState,
            contract = contract
        )
        PostStatus.PUBLISHED -> PostStatusPublished(
            contract = contract,
        )
        PostStatus.ERROR -> PostStatusError(
            contract = contract
        )
        PostStatus.MISSING_AUTH -> PostStatusMissingAuth()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostStatusWriting(
    uiState: PostCreationUiState,
    contract: PostCreationContract? = null,
){
    val context = LocalContext.current

    Column(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(uiState.post.author.profileImageUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = null,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp)
                            .size(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                    )
                    Column {
                        Text(
                            text = uiState.post.author.displayName,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
                val interactionSource = remember { MutableInteractionSource() }
                BasicTextField(
                    modifier = Modifier.padding(vertical = 16.dp).fillMaxWidth(),
                    value = uiState.post.content,
                    onValueChange = {
                        contract?.onPostContentChanged(it)
                    },
                    textStyle = TextStyle(
                        fontSize = 22.sp,
                        lineHeight = 28.sp
                    ),
                    decorationBox = @Composable { innerTextField ->
                        TextFieldDefaults.DecorationBox(
                            value = uiState.post.content,
                            innerTextField = innerTextField,
                            enabled = true,
                            singleLine = false,
                            visualTransformation = VisualTransformation.None,
                            interactionSource = interactionSource,
                            placeholder = {
                                Text(
                                    text = "Type here...",
                                    style = TextStyle(
                                        fontSize = 22.sp,
                                        lineHeight = 28.sp
                                    ),
                                )
                            },
                            container = {
                                Container(
                                    enabled = false,
                                    isError = false,
                                    interactionSource = interactionSource,
                                    focusedBorderThickness = 0.dp,
                                    unfocusedBorderThickness = 0.dp,
                                )
                            }
                        )
                    }
                )
            }
        }
        Button(
            modifier = Modifier.align(Alignment.End).padding(top = 12.dp),
            enabled = uiState.post.content.isNotEmpty(),
            onClick = {
                contract?.publishPost(uiState.post)
            }
        ) {
            Text(
                text = "Publish",
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun PostStatusPublished(
    contract: PostCreationContract? = null,
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            imageVector = Icons.Outlined.CheckCircle,
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
        Text(
            text = "Post published!",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { contract?.returnToWriting() }) {
            Text("New Post")
        }
    }
}

@Composable
fun PostStatusError(
    contract: PostCreationContract? = null,
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            imageVector = Icons.Outlined.ErrorOutline,
            contentDescription = null,
            modifier = Modifier.size(200.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error)
        )
        Text(
            text = "Error publishing post",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { contract?.returnToWriting() }) {
            Text("Back")
        }
    }
}

@Composable
fun PostStatusMissingAuth() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            imageVector = Icons.AutoMirrored.Default.Login,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Please log in to create a post",
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PostCreationScreenEmptyTextPreview(){
    PostCreationScreen(
        uiState = PostCreationUiState(Post(
            author = UserProfile(displayName = "John Doe")
        ))
    )
}

@Preview(showBackground = true)
@Composable
fun PostCreationScreenPreview(){
    PostCreationScreen(
        uiState = PostCreationUiState(Post(
            content = loremIpsum,
            author = UserProfile(displayName = "John Doe")
        ))
    )
}

@Preview(showBackground = true)
@Composable
fun PostCreationScreenPostPublishedPreview(){
    PostCreationScreen(
        uiState = PostCreationUiState(
            status = PostStatus.PUBLISHED
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PostCreationScreenPostErrorPreview(){
    PostCreationScreen(
        uiState = PostCreationUiState(
            status = PostStatus.ERROR
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PostCreationScreenMissingAuthPreview(){
    PostCreationScreen(
        uiState = PostCreationUiState(
            status = PostStatus.MISSING_AUTH
        )
    )
}