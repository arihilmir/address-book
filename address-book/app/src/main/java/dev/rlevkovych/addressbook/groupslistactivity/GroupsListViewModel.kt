package dev.rlevkovych.addressbook.groupslistactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import dev.rlevkovych.addressbook.data.ContactsRepository
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupsListViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ContactsRepository

    init {
        val dao = ContactsDataBase.getInstance(application).contactDao()
        repository = ContactsRepository(dao)
    }
    var availableGroups = repository.allGroups
}