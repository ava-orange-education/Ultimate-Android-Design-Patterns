package com.example.contacts.intent

import com.example.core.model.Contact

data class ContactListState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)