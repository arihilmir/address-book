package dev.rlevkovych.addressbook.data

import android.os.AsyncTask
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
    val contactsFromActiveGroups: LiveData<List<Contact>> = contactsDao.getContactsFromActiveGroups()

    fun getContact(id: String) : LiveData<Contact?> {
        return contactsDao.getContactsById(id)
    }

    fun insert(contact: Contact) {
        InsertAsyncTask(contactsDao).execute(contact)
    }

    fun update(contact: Contact) {
        UpdateAsyncTask(contactsDao).execute(contact)
    }

    fun delete(id: String) {
        DeleteAsyncTask(contactsDao).execute(id)
    }

    private class InsertAsyncTask internal constructor(private val insertContactsDao: ContactsDao) : AsyncTask<Contact, Void, Void>() {

        override fun doInBackground(vararg params: Contact): Void? {
            insertContactsDao.insert(params[0])
            return null
        }
    }

    private class UpdateAsyncTask internal constructor(private val updateContactsDao: ContactsDao) : AsyncTask<Contact, Void, Void>() {

        override fun doInBackground(vararg params: Contact): Void? {
            updateContactsDao.updateTask(params[0])
            return null
        }
    }

    private class DeleteAsyncTask internal constructor(private val deleteContactsDao: ContactsDao) : AsyncTask<String, Void, Void>() {

        override fun doInBackground(vararg id: String): Void? {
            deleteContactsDao.deleteContact(id[0])
            return null
        }
    }

    fun insert(group: Group) {
        coroutineScope.launch {
            contactsDao.insert(group)
        }
    }
}