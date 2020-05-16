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
import androidx.room.Update
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.contactsdetail.ContactsDetailActivity
import dev.rlevkovych.addressbook.data.Contact

class EditContactActivity : AppCompatActivity() {

    private lateinit var viewModel: EditContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_contanct)

        val contactId = intent.getStringExtra("contactId")
        var contact: Contact? = Contact("", "");

        viewModel = ViewModelProviders.of(this).get(EditContactViewModel::class.java)
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

            val zip = findViewById<TextView>(R.id.userZip).text.toString()

            var newContact = Contact(
                findViewById<TextView>(R.id.userName).text.toString(),
                findViewById<TextView>(R.id.userPhone).text.toString(),
                findViewById<TextView>(R.id.userEmail).text.toString(),
                findViewById<TextView>(R.id.userStreet).text.toString(),
                findViewById<TextView>(R.id.userCity).text.toString(),
                findViewById<TextView>(R.id.userState).text.toString(),

                if(zip == "") null else zip.toInt()
            );

            if(contactId != null){
                newContact.id = contactId
                viewModel.update(newContact)
            }
            else{
                viewModel.create(newContact)
            }

            val intent = Intent(this, ContactsDetailActivity::class.java).apply {
                putExtra("contactId", contactId)
            }
            startActivity(intent)
        }
    }
}
