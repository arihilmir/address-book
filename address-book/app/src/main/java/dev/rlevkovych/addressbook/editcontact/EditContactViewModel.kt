package dev.rlevkovych.addressbook.editcontact

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.data.ContactsRepository
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase

class EditContactViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ContactsRepository

    val allContacts: LiveData<List<Contact>>

    init {
        val contactsDao = ContactsDataBase.getInstance(application).contactDao()
        repository = ContactsRepository(contactsDao)
        allContacts = repository.allContacts
    }

    fun create(contact: Contact) {
        repository.insert(contact)
    }

    fun update(contact: Contact) {
        repository.update(contact)
    }
}