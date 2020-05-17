package dev.rlevkovych.addressbook.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.data.entities.Group
import dev.rlevkovych.addressbook.data.entities.GroupContactRelation

@Database(entities = [Contact::class, Group::class, GroupContactRelation::class], version = 1)
abstract class ContactsDataBase : RoomDatabase() {
    abstract fun contactDao(): ContactsDao

    companion object {
        private var INSTANCE: ContactsDataBase? = null

        private val lock = Any()

        fun getInstance(context: Context): ContactsDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ContactsDataBase::class.java, "Contacts.db")
                        .createFromAsset("init_data.db")
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}