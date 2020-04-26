package dev.rlevkovych.addressbook

import android.app.Application
import androidx.room.Room
import dev.rlevkovych.addressbook.data.source.local.ContactsDataBase

class AddressBookApp : Application() {
    lateinit var db: ContactsDataBase

    companion object {
        var instance: AddressBookApp? = null
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            ContactsDataBase::class.java, "Contacts.db"
        )
            .allowMainThreadQueries()
            .createFromAsset("init_data.db")
            .build()
        instance = this
    }
}