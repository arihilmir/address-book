package dev.rlevkovych.addressbook.contactslist

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactsdetail.ContactsDetailActivity
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.editcontact.EditContactActivity
import dev.rlevkovych.addressbook.groupslistactivity.GroupsListActivity
import kotlinx.android.synthetic.main.activity_contacts_list.*

class ContactsListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsListViewModel
    private lateinit var contactId: String
    private lateinit var contactsAdapter: ContactsRecyclerAdapter
    private lateinit var contactsAll: MutableList<Contact>
    private lateinit var contactsDisplayed: MutableList<Contact>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        val option = AccountDisplayOption
            .valueOf(intent.getStringExtra("dataConfig") ?: "All")
        viewModel =
            ViewModelProvider(this, CustomViewModelFactory(application, option))
                .get(ContactsListViewModel::class.java)

        configRecyclerView()
        configNavigation()

    }

    private fun configRecyclerView() {
        contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ContactsListActivity)
            adapter = contactsAdapter
        }

        contactsAdapter.onItemClick = { contact ->
            val intent = Intent(this, ContactsDetailActivity::class.java).apply {
                putExtra("contactId", contact.id)
            }
            startActivity(intent)
        }

        viewModel.allContacts.observe(this, Observer { items ->
            if (items.isNotEmpty()) {
                contactId = items.first().id
                contactsAll = items.toMutableList()
                contactsDisplayed = items.toMutableList()

                contactsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@ContactsListActivity)
                    contactsAdapter = ContactsRecyclerAdapter()
                    adapter = contactsAdapter
                }
                contactsAdapter.submitList(contactsDisplayed)
                contactsAdapter.onItemClick = { contact ->
                    val intent = Intent(this, ContactsDetailActivity::class.java).apply {
                        putExtra("contactId", contact.id)
                    }
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun configNavigation() {
        navigation_button.setOnClickListener {
            val intent = Intent(this, GroupsListActivity::class.java)
            startActivity(intent)
        }

        openContact.setOnClickListener {
            val intent = Intent(this, EditContactActivity::class.java)
            startActivity(intent)
        }

        val searchView = findViewById<SearchView>(R.id.contactsSearchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!!.isNotEmpty()) {
                    contactsDisplayed.clear()
                    val searchTerm = newText.toString().toLowerCase()

                    contactsAll.forEach {
                        if (it.name.toLowerCase().contains(searchTerm)) {
                            contactsDisplayed.add(it)
                        }
                    }

                    contactsAdapter.notifyDataSetChanged()
                } else {
                    contactsDisplayed.clear()
                    contactsDisplayed.addAll(contactsAll)
                    contactsAdapter.notifyDataSetChanged()
                }
                return true
            }

        })
    }

    class CustomViewModelFactory(
        private val application: Application,
        private val option: AccountDisplayOption
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContactsListViewModel(application, option) as T
        }

    }
}
