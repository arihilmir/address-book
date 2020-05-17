package dev.rlevkovych.addressbook.groupslistactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import dev.rlevkovych.addressbook.data.ContactsRepository
import dev.rlevkovych.addressbook.data.entities.Group

class GroupsListAdapter(private val repository: ContactsRepository) : RecyclerView.Adapter<GroupViewHolder>() {
    private var groups: List<Group>? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GroupViewHolder(inflater, parent)
    }

    fun setData(newGroups: List<Group>) {
        groups = newGroups
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = groups?.size ?: 0

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        val value = groups?.get(position)!!
        holder.isDisplayed.tag = value
        holder.bind(value)
        holder.isDisplayed.setOnClickListener {
            val item = (it as CheckBox).tag as Group
            item.isActive = !item.isActive
            repository.insert(item)
        }
    }

}