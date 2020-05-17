package dev.rlevkovych.addressbook.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import dev.rlevkovych.addressbook.data.entities.Contact
import dev.rlevkovych.addressbook.data.entities.Group

@Dao interface ContactsDao {
    @Query("SELECT * FROM Contact ORDER BY Contact.name") fun getContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM Contact WHERE id=:id")
    fun getContactsById(id: String): LiveData<Contact?>

    @Insert(onConflict = OnConflictStrategy.REPLACE) fun insert(contact: Contact)
    @Update fun updateTask(contact: Contact): Int
    @Query("DELETE FROM Contact WHERE id=:id") fun deleteContact(id: String)

    @Query("SELECT * FROM `Group`") fun getGroups(): LiveData<List<Group>>
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insert(group: Group)

    @Query("SELECT * FROM Contact EXCEPT SELECT Contact.* FROM Contact INNER JOIN `Group` ON Contact.`group` = `Group`.name WHERE NOT is_active ORDER BY Contact.name")
    fun getContactsFromActiveGroups(): LiveData<List<Contact>>
}