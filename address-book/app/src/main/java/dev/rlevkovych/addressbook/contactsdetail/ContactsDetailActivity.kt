package dev.rlevkovych.addressbook.contactsdetail

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactslist.ContactsListActivity
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.editcontact.EditContactActivity

class ContactsDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ContactsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_detail)

        val contactId = intent.getStringExtra("contactId")
        var contact: Contact? = Contact("", "");

        viewModel = ViewModelProviders.of(this).get(ContactsDetailViewModel::class.java)
        viewModel.allContacts.observe(this, Observer { items ->
            if (items.isNotEmpty()) {
                contact = items.find { contact -> contact.id == contactId }

                if (contact != null) {
                    val userName = findViewById<TextView>(R.id.userName)
                    userName.text = contact?.name;

                    val userPhone = findViewById<TextView>(R.id.userPhone)
                    userPhone.text = contact?.phoneNumber;

                    val userEmail = findViewById<TextView>(R.id.userEmail)
                    userEmail.text = contact?.email

                    if (contact!!.street != null) {
                        val userStreet = findViewById<TextView>(R.id.userStreet)
                        userStreet.text = contact?.street;
                    } else {
                        val streetLabel = findViewById<TextView>(R.id.streetLabel)
                        streetLabel.visibility = View.INVISIBLE;

                        val userStreet = findViewById<TextView>(R.id.userStreet)
                        userStreet.visibility = View.INVISIBLE;

                        val streetDivider = findViewById<View>(R.id.streetDivider)
                        streetDivider.visibility = View.INVISIBLE;
                    }

                    if (contact!!.city != null) {
                        val userCity = findViewById<TextView>(R.id.userCity)
                        userCity.text = contact?.city;
                    } else {
                        val cityLabel = findViewById<TextView>(R.id.cityLabel)
                        cityLabel.visibility = View.INVISIBLE;

                        val userCity = findViewById<TextView>(R.id.userCity)
                        userCity.visibility = View.INVISIBLE;

                        val cityDivider = findViewById<View>(R.id.cityDivider)
                        cityDivider.visibility = View.INVISIBLE;
                    }

                    if (contact!!.state != null) {
                        val userState = findViewById<TextView>(R.id.userState)
                        userState.text = contact?.state;
                    } else {
                        val stateLabel = findViewById<TextView>(R.id.stateLabel)
                        stateLabel.visibility = View.INVISIBLE;

                        val userState = findViewById<TextView>(R.id.userState)
                        userState.visibility = View.INVISIBLE;

                        val stateDivider = findViewById<View>(R.id.stateDivider)
                        stateDivider.visibility = View.INVISIBLE;
                    }

                    if (contact!!.zip != null) {
                        val userZip = findViewById<TextView>(R.id.userZip)
                        userZip.text = contact?.zip.toString();
                    } else {
                        val zipLabel = findViewById<TextView>(R.id.zipLabel)
                        zipLabel.visibility = View.INVISIBLE;

                        val userZip = findViewById<TextView>(R.id.userZip)
                        userZip.visibility = View.INVISIBLE;

                        val zipDivider = findViewById<View>(R.id.zipDivider)
                        zipDivider.visibility = View.INVISIBLE;
                    }
                } else {
                    Toast.makeText(this, "Successfully deleted!", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "No data", Toast.LENGTH_LONG).show()
            }
        })

        val backButton = findViewById<ImageButton>(R.id.backBtn)
        backButton.setOnClickListener {
            val intent = Intent(this, ContactsListActivity::class.java)
            startActivity(intent)
        }

        val editButton = findViewById<ImageButton>(R.id.editBtn)
        editButton.setOnClickListener {
            val intent = Intent(this, EditContactActivity::class.java).apply {
                putExtra("contactId", contactId)
            }
            startActivity(intent)
        }

        val deleteBtn = findViewById<ImageButton>(R.id.saveBtn)
        deleteBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("This will permanently delete the contact")
                .setNeutralButton("CANCEL") { dialog, which ->
                    dialog.cancel();
                }
                .setPositiveButton("DELETE") { dialog, which ->
                    viewModel.delete(contactId)
                    val intent = Intent(this, ContactsListActivity::class.java)
                    startActivity(intent)
                }
                .show()
        }
    }
}
