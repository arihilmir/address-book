package dev.rlevkovych.addressbook.groupslistactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GroupsListAdapter(private val groups: List<Group>) : RecyclerView.Adapter<GroupViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GroupViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int)
            = holder.bind(groups[position])
}