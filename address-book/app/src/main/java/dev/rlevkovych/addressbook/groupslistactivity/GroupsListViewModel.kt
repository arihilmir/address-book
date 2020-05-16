package dev.rlevkovych.addressbook.groupslistactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel

data class Group(val name: String, var isActive: Boolean)

class GroupsListViewModel(application: Application): AndroidViewModel(application) {
    var availableGroups = listOf(Group("Family", true), Group("Work", false), Group("Friends", true))
//            get() { return  }
}