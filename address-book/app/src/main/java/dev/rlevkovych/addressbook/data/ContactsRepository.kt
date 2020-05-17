package dev.rlevkovych.addressbook.data

import androidx.lifecycle.LiveData
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.data.entities.Group
import dev.rlevkovych.addressbook.data.source.local.ContactsDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsRepository(private val contactsDao: ContactsDao) {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val allContacts: LiveData<List<Contact>> = contactsDao.getContacts()
    val allGroups: LiveData<List<Group>> = contactsDao.getGroups()

    fun getContact(id: String) : LiveData<Contact?> {
        return contactsDao.getContactsById(id)
    }

    fun insert(contact: Contact) {
        contactsDao.insert(contact)
    }

    fun insert(group: Group) {
        coroutineScope.launch {
            contactsDao.insert(group)
        }
    }
}