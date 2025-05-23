package com.example.contacts.intent

sealed class ContactListIntent {
    object LoadContacts: ContactListIntent()
}