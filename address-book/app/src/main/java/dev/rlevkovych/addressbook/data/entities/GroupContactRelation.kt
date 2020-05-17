package dev.rlevkovych.addressbook.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["groupName", "userId"], tableName = "group_contact_rels")
data class GroupContactRelation(
    val groupName: String,
    val userId: String
)