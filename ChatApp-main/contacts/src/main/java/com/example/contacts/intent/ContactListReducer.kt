package com.example.contacts.intent

import com.example.contacts.model.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContactListReducer(
    private val repository: ContactRepository
) {

    private val _state = MutableStateFlow(ContactListState())
    val state: StateFlow<ContactListState> get() = _state.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun process(intent: ContactListIntent) {
        when (intent) {
            is ContactListIntent.LoadContacts -> loadContacts()
        }
    }

    fun loadContacts() {
        _state.value = _state.value.copy(isLoading = true)
        scope.launch {
            try {
                val contacts = repository.fetchContacts()
                _state.value = ContactListState(contacts = contacts, isLoading = false)
            } catch (e: Exception) {
                _state.value = ContactListState(isLoading = false, error = e.message)
            }
        }
    }

}