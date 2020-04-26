package dev.rlevkovych.addressbook.data

import androidx.lifecycle.LiveData
import dev.rlevkovych.addressbook.data.source.local.ContactsDao

class ContactsRepository(private val contactsDao: ContactsDao) {
    val allContacts: LiveData<List<Contact>> = contactsDao.getContacts()

    fun getContact(id: String) : LiveData<Contact?> {
        return contactsDao.getContactsById(id)
    }

    fun insert(contact: Contact) {
        contactsDao.insert(contact)
    }
}