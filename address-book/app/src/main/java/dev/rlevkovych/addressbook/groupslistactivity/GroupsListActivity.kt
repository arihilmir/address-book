package dev.rlevkovych.addressbook.groupslistactivity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactslist.AccountDisplayOption
import dev.rlevkovych.addressbook.contactslist.ContactsListActivity
import kotlinx.android.synthetic.main.activity_groups_list.*
import java.util.logging.Logger

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.hideKeyboard() {
    view?.let {
        activity?.hideKeyboard(it)
    }
}

class GroupsListActivity : AppCompatActivity() {

    private lateinit var viewModel: GroupsListViewModel
    private val intentConfigKey = "dataConfig"
    private lateinit var groupsAdapter: GroupsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_groups_list)
        viewModel = ViewModelProvider(this).get(GroupsListViewModel::class.java)
        groupsAdapter = GroupsListAdapter()

        configRecyclerView()
        configNavigation()
        configSearch()
    }

    override fun onPause() {
        super.onPause()
        viewModel.saveGroupChanges(groupsAdapter.modifiedGroups)
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
        search_groups.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.findGroups(newText ?: "").observe(this@GroupsListActivity, Observer {
                    groupsAdapter.setData(it)
                })
                return true
            }

        })
    }
}
