package com.example.contacts.model

import com.example.core.db.ContactDao
import com.example.core.db.ContactEntity
import com.example.core.model.Contact

class ContactRepositoryImpl(
    private val contactDao: ContactDao,
) : ContactRepository {

    override suspend fun fetchContacts(): List<Contact> {
        return contactDao.getAll()
            .map { it.toContact() }
    }

    fun ContactEntity.toContact(): Contact {
        return Contact(
            id = id,
            name = name,
            phoneNumber = phoneNumber,
            profilePicture = profilePicture
        )
    }
}