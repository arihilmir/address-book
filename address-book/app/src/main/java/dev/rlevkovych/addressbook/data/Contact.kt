package dev.rlevkovych.addressbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Contact")
data class Contact constructor(
    @ColumnInfo val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo val email: String = "",
    @ColumnInfo val street: String = "",
    @ColumnInfo val city: String = "",
    @ColumnInfo val state: String = "",
    @ColumnInfo val zip: Int = 0,
    @PrimaryKey @ColumnInfo val id: String = UUID.randomUUID().toString()
    )