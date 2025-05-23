package com.example.contacts.model

import com.example.core.model.Contact

interface ContactRepository {

    suspend fun fetchContacts(): List<Contact>

}
