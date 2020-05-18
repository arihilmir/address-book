package dev.rlevkovych.addressbook.contactslist

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.data.ContactsRepository
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase

enum class AccountDisplayOption {
    All, Groups
}

class ContactsListViewModel(application: Application, option: AccountDisplayOption, groups: List<String>?): AndroidViewModel(application) {
    private val repository: ContactsRepository
    private val activeGroups: List<String>?

    val allContacts: LiveData<List<Contact>>

    init {
        val contactsDao = ContactsDataBase.getInstance(application).contactDao()
        repository = ContactsRepository(contactsDao)
        activeGroups = groups
        Log.e("ASDFASDFASDFASDFASD", groups?.joinToString(",") ?: "EMPTY")
        allContacts = if (activeGroups != null) {
            Transformations.map(repository.allContacts) {
                Log.e("ASDFASDFASDFASDFASDFa", "HERE")
                it.filter {contact ->
                    Log.e("ASDFSADFASDFASDFASFAsd", contact.group + " " + contact.name)
                    if (contact.group != null) {
                        Log.e("ASDFSADFASDFASDFASFAsd", contact.group + " " + contact.name)
                        return@filter activeGroups.contains(contact.group)
                    } else {
                        Log.e("ASDFASDFASDFASDFASDFa", "HE1R1E")
                        return@filter true
                    }
                }
            }
        } else {
            repository.allContacts
        }
//        allContacts = if (option == AccountDisplayOption.All)
//            repository.allContacts
//        else
//            repository.contactsFromActiveGroups
    }
}