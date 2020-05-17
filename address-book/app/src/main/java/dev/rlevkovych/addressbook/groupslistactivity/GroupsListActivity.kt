package dev.rlevkovych.addressbook.groupslistactivity

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactslist.AccountDisplayOption
import dev.rlevkovych.addressbook.contactslist.ContactsListActivity
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase
import kotlinx.android.synthetic.main.activity_groups_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GroupsListActivity : AppCompatActivity() {

    private lateinit var viewModel: GroupsListViewModel
    private val intentConfigKey = "dataConfig"
    lateinit var groupsAdapter: GroupsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_list)
        viewModel = ViewModelProvider(this).get(GroupsListViewModel::class.java)
        groupsAdapter = GroupsListAdapter(viewModel.repository)

        configRecyclerView()
        configNavigation()
        configSearch()
    }

    private fun configNavigation() {
        display_all_contacts.setOnClickListener {
            val intent = Intent(this, ContactsListActivity::class.java).apply {
                putExtra(intentConfigKey, AccountDisplayOption.All.toString())
            }
            startActivity(intent)
        }

        display_contacts_by_group.setOnClickListener {
            val intent = Intent(this, ContactsListActivity::class.java).apply {
                putExtra(intentConfigKey, AccountDisplayOption.Groups.toString())
            }
            startActivity(intent)
        }
    }

    private fun configRecyclerView() {
        groups_list.apply {
            layoutManager = LinearLayoutManager(this@GroupsListActivity)
            adapter = groupsAdapter
        }

        viewModel.availableGroups.observe(this, Observer {
            groupsAdapter.setData(it)
        })
    }

    private fun configSearch() {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                viewModel.findGroups(query).observe(this, Observer {
                    groupsAdapter.setData(it)
                    Toast.makeText(applicationContext, it.size, Toast.LENGTH_LONG)
                })
            }
        }
    }
}
