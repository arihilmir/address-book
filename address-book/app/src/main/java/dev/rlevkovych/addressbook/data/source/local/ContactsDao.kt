package dev.rlevkovych.addressbook.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.rlevkovych.addressbook.data.Contact

@Dao interface ContactsDao {
    @Query("SELECT * FROM Contact") fun getContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE id=:id")
    fun getContactsById(id: String): LiveData<Contact?>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(contact: Contact)
    @Update fun updateTask(contact: Contact): Int
    @Query("DELETE FROM Contact WHERE id=:id") fun deleteContact(id: String)
}