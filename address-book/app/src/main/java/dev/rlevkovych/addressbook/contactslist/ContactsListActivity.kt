package dev.rlevkovych.addressbook.contactslist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactsdetail.ContactsDetailActivity
import dev.rlevkovych.addressbook.editcontact.EditContactActivity
import kotlinx.android.synthetic.main.activity_contacts_list.*

class ContactsListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsListViewModel
    private lateinit var contactId: String
    private lateinit var contactsAdapter: ContactsRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        viewModel = ViewModelProviders.of(this).get(ContactsListViewModel::class.java)
        viewModel.allContacts.observe(this, Observer { items ->
            if (items.isNotEmpty()) {
                contactId = items.first().id

                contactsRecyclerView.apply {
                    layoutManager = LinearLayoutManager(this@ContactsListActivity)
                    contactsAdapter = ContactsRecyclerAdapter()
                    adapter = contactsAdapter
                }
                contactsAdapter.submitList(items)
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
    }
}
