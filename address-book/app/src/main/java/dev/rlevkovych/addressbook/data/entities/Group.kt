package dev.rlevkovych.addressbook.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Group")
data class Group constructor(
    @ColumnInfo @PrimaryKey val name: String,
    @ColumnInfo(name = "is_active") var isActive: Boolean
)