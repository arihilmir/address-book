package dev.rlevkovych.addressbook.data

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import dev.rlevkovych.addressbook.data.source.local.ContactsDao

class ContactsRepository(private val contactsDao: ContactsDao) {
    val allContacts: LiveData<List<Contact>> = contactsDao.getContacts()

    fun getContact(id: String) : LiveData<Contact?> {
        return contactsDao.getContactsById(id)
    }

//    fun insert(contact: Contact) {
//        contactsDao.insert(contact)
//    }

    fun insert(contact: Contact) {
        insertAsyncTask(contactsDao).execute(contact)
    }

//    fun update(contact: Contact) {
//        contactsDao.updateTask(contact)
//    }

    fun update(contact: Contact) {
        updateAsyncTask(contactsDao).execute(contact)
    }

    private class insertAsyncTask internal constructor(private val insertcontactsDao: ContactsDao) : AsyncTask<Contact, Void, Void>() {

        override fun doInBackground(vararg params: Contact): Void? {
            insertcontactsDao.insert(params[0])
            return null
        }
    }

    private class updateAsyncTask internal constructor(private val updatecontactsDao: ContactsDao) : AsyncTask<Contact, Void, Void>() {

        override fun doInBackground(vararg params: Contact): Void? {
            updatecontactsDao.updateTask(params[0])
            return null
        }
    }
}