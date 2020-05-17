package dev.rlevkovych.addressbook.groupslistactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase
import kotlinx.android.synthetic.main.activity_groups_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupsListActivity : AppCompatActivity() {

    private lateinit var viewModel: GroupsListViewModel
    var groupsAdapter = GroupsListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_list)
        viewModel = ViewModelProviders.of(this).get(GroupsListViewModel::class.java)


        groups_list.apply {
            layoutManager = LinearLayoutManager(this@GroupsListActivity)
            adapter = groupsAdapter
        }

        viewModel.availableGroups.observe(this, Observer {
            groupsAdapter.setData(it)
            Toast.makeText(applicationContext, it.size.toString(), Toast.LENGTH_LONG).show()
        })
    }
}
