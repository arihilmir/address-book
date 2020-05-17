package dev.rlevkovych.addressbook.contactslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.data.ContactsRepository
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase

enum class AccountDisplayOption {
    All, Groups
}

class ContactsListViewModel(application: Application, option: AccountDisplayOption): AndroidViewModel(application) {
    private val repository: ContactsRepository

    val allContacts: LiveData<List<Contact>>

    init {
        val contactsDao = ContactsDataBase.getInstance(application).contactDao()
        repository = ContactsRepository(contactsDao)
        allContacts = if (option == AccountDisplayOption.All)
            repository.allContacts
        else
            repository.contactsFromActiveGroups
    }
}