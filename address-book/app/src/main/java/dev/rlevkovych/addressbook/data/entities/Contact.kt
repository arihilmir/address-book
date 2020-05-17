package dev.rlevkovych.addressbook.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "Contact",
    foreignKeys = [ForeignKey(
        entity = Group::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("group"),
        onDelete = ForeignKey.SET_NULL
    )]
)
data class Contact constructor(
    @ColumnInfo val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo val email: String = "",
    @ColumnInfo val street: String? = "",
    @ColumnInfo val city: String? = "",
    @ColumnInfo val state: String? = "",
    @ColumnInfo val zip: Int? = 0,
    @PrimaryKey @ColumnInfo var id: String = UUID.randomUUID().toString(),
    @ColumnInfo val group: String? = ""
)