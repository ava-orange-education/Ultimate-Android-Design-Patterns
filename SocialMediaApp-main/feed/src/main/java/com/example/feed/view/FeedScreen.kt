package com.example.feed.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.automirrored.outlined.Comment
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.core.formatTimestamp
import com.example.core.loremIpsum
import com.example.core.model.Comment
import com.example.core.model.Post
import com.example.core.model.UserProfile
import com.example.feed.viewmodel.FeedContract

data class PostUiState(
    val loggedUser: UserProfile = UserProfile(),
    val post: Post = Post(),
    val isLiked: Boolean = false,
    val isCommented: Boolean = false
)

@Composable
fun PostOnFeed(
    uiState: PostUiState,
    contract: FeedContract? = null
){
    val context = LocalContext.current
    Card(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
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
                    Text(
                        text = uiState.post.timestamp.formatTimestamp(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.6f)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (uiState.loggedUser.username == uiState.post.author.username) {
                    Image(
                        modifier = Modifier.padding(8.dp).align(Alignment.Top)
                            .clickable {
                                contract?.deletePost(uiState.post.id.toString())
                            },
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
            }
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = uiState.post.content,
                fontSize = 22.sp,
                lineHeight = 28.sp
            )
            if (uiState.post.imageUrl.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uiState.post.imageUrl)
                        .crossfade(true)
                        .build(),
                    placeholder = null,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(bottom = 8.dp)
                        .fillMaxWidth().height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier.clickable {
                            contract?.likeOrUnlikePost(uiState.post.id.toString())
                        },
                        imageVector = if (uiState.isLiked)
                            Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "Like Button",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = uiState.post.likes.size.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        imageVector = if (uiState.isCommented)
                            Icons.AutoMirrored.Default.Comment
                        else Icons.AutoMirrored.Outlined.Comment,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = uiState.post.comments.size.toString(),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun FeedScreen(
    uiState: FeedUiState,
    contract: FeedContract? = null
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Feed", "Your Posts")

    LaunchedEffect(Unit) {
        contract?.fetchPosts()
    }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    },
                    content = {
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = title,
                            fontSize = 22.sp
                        )
                    }
                )
            }
        }
        val postsToShow = when (selectedTabIndex) {
            0 -> uiState.posts
            1 -> uiState.posts.filter { it.author.username == uiState.loggedUser.username }
            else -> uiState.posts
        }
        LazyColumn {
            items(postsToShow.size) {
                val post = postsToShow[it]
                val postId = post.id.toString()
                val postUiState = PostUiState(
                    loggedUser = uiState.loggedUser,
                    post = post,
                    isLiked = contract?.isPostLiked(postId) == true,
                    isCommented = contract?.isPostCommented(postId) == true,
                )
                PostOnFeed(postUiState, contract)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedScreenPreview() {
    FeedScreen(uiState = FeedUiState())
}

val likes = mutableListOf<UserProfile>().apply {
    listOf("alice", "bob", "charlie", "david", "eve").forEach {
        add(UserProfile(username = it))
    }
}

val comments = mutableListOf<Comment>().apply {
    repeat(2) {
        add(Comment())
    }
}

@Preview(showBackground = true)
@Composable
fun PostOnFeedPreview() {
    PostOnFeed(uiState = PostUiState(
        loggedUser = UserProfile(username = "john_doe"),
        post = Post(
            author = UserProfile(
                displayName = "John Doe",
                username = "john_doe"
            ),
            content = loremIpsum,
            likes = likes,
            comments = comments,
            timestamp = System.currentTimeMillis()
        )
    ))
}

@Preview(showBackground = true)
@Composable
fun PostOnFeedLikedAndCommentedPreview() {
    PostOnFeed(uiState = PostUiState(
        loggedUser = UserProfile(username = "john_doe"),
        post = Post(
            author = UserProfile(
                displayName = "Author",
            ),
            content = loremIpsum,
            likes = likes,
            comments = comments,
            timestamp = System.currentTimeMillis()
        ),
        isLiked = true,
        isCommented = true
    ))
}