package dev.rlevkovych.addressbook.contactslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rlevkovych.addressbook.R
import org.w3c.dom.Text

class ContactsListActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsListViewModel
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)

        viewModel = ViewModelProviders.of(this).get(ContactsListViewModel::class.java)
        textView = findViewById(R.id.text)
        viewModel.allContacts.observe(this, Observer { items ->
            textView.text = items.first().toString()
        })
    }
}
