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

        val configValue = intent.getStringExtra("dataConfig")
        var option: AccountDisplayOption
        if (configValue == null)
            option = AccountDisplayOption.All
        else if (configValue == "all")
            option = AccountDisplayOption.All
        else
            option = AccountDisplayOption.Groups

        viewModel = ViewModelProviders.of(this, CustomViewModelFactory(this.getApplication(), option)).get(ContactsListViewModel::class.java)
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

        val button = findViewById<Button>(R.id.openContact)
        button.setOnClickListener {
            val intent = Intent(this, EditContactActivity::class.java).apply {
            }

            startActivity(intent)
        }
        val nav_btn = findViewById<Button>(R.id.navigation_button)
        if (option == AccountDisplayOption.All)
            nav_btn.text = "Groups"
        else
            nav_btn.text = "All"
        nav_btn.setOnClickListener {
            val intent = Intent(this, ContactsListActivity::class.java).apply {
                putExtra("dataConfig", nav_btn.text.toString().toLowerCase())
            }

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

    class CustomViewModelFactory(private val application: Application, private val option: AccountDisplayOption) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ContactsListViewModel(application, option) as T
        }

    }
}
