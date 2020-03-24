package dev.rlevkovych.addressbook.contactslist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.rlevkovych.addressbook.R

class ContactsListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts_list)
    }
}
