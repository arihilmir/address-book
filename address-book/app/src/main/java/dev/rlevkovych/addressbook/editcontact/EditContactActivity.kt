package dev.rlevkovych.addressbook.editcontact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactsdetail.ContactsDetailActivity
import dev.rlevkovych.addressbook.contactslist.ContactsListActivity
import dev.rlevkovych.addressbook.contactslist.ContactsListViewModel
import dev.rlevkovych.addressbook.data.Contact

class EditContactActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contanct)

        val contactId = intent.getStringExtra("contactId")
        var contact: Contact? = Contact("", "");

        viewModel = ViewModelProviders.of(this).get(ContactsListViewModel::class.java)
        viewModel.allContacts.observe(this, Observer { items ->
            if (items.isNotEmpty()) {
                contact = items.find { contact -> contact.id == contactId }
                Log.i("contact", contact!!.name);

                val userName = findViewById<TextView>(R.id.userName)
                userName.text = contact!!.name;

                val userPhone = findViewById<TextView>(R.id.userPhone)
                userPhone.text = contact!!.phoneNumber;

                val userEmail = findViewById<TextView>(R.id.userEmail)
                userEmail.text = contact!!.email

                val userStreet = findViewById<TextView>(R.id.userStreet)
                userStreet.text = contact!!.street;

                val userCity = findViewById<TextView>(R.id.userCity)
                userCity.text = contact!!.city;

                val userState = findViewById<TextView>(R.id.userState)
                userState.text = contact!!.state

                if (contact!!.zip != null) {
                    val userZip = findViewById<TextView>(R.id.userZip)
                    userZip.text = contact!!.zip.toString();
                } else {
                    val userZip = findViewById<TextView>(R.id.userZip)
                    userZip.text = "";
                }
            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_LONG).show()
            }
        })

        val saveBtn = findViewById<ImageButton>(R.id.saveBtn)
        saveBtn.setOnClickListener {
            // TODO Save edited contact here.
            val intent = Intent(this, ContactsDetailActivity::class.java).apply {
                putExtra("contactId", contactId)
            }
            startActivity(intent)
        }
    }
}
