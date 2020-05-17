package dev.rlevkovych.addressbook.groupslistactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import dev.rlevkovych.addressbook.data.ContactsRepository
import dev.rlevkovych.addressbook.data.entities.Group
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupsListViewModel(application: Application): AndroidViewModel(application) {
    val repository: ContactsRepository

    init {
        val dao = ContactsDataBase.getInstance(application).contactDao()
        repository = ContactsRepository(dao)
    }

    var availableGroups = repository.allGroups

    fun findGroups(withName: String): LiveData<List<Group>> {
        return repository.findGroups(withName)
    }

    fun saveGroupChanges(groups: List<Group>) {
        groups.forEach{
            repository.insert(it)
        }
    }
}