package com.example.contacts.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.contacts.intent.ContactListState
import com.example.core.formatPhoneNumber
import com.example.core.model.Contact
import com.example.core.stringToColor

@Composable
fun ContactListScreen(
    state: ContactListState,
    onContactSelected: (Int) -> Unit,
){
    LazyColumn {
        items(state.contacts.size) { index ->
            val contact = state.contacts[index]
            ContactListItem(
                contact = contact,
                onContactSelected = onContactSelected
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListItem(
    contact: Contact,
    onContactSelected: (Int) -> Unit
) {
    val context = LocalContext.current
    Row (
        modifier = Modifier.fillMaxWidth()
            .combinedClickable(
                onClick = { onContactSelected(contact.id) }
            )
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val profileImageModifier = Modifier.padding(end = 8.dp)
            .size(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(stringToColor(contact.name))
        if (contact.profilePicture.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(contact.profilePicture)
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
        Column {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.alpha(0.5f),
                    text = formatPhoneNumber(contact.phoneNumber),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatListScreenPreview(){
    val contacts = mutableListOf<Contact>()
    listOf("John Doe", "Jane Doe", "Bob Smith", "Alice Johnson").forEach {
        contacts.add(Contact(name = it, phoneNumber = "+11234567890"))
    }
    ContactListScreen(
        state = ContactListState(contacts = contacts),
        onContactSelected = {}
    )
}