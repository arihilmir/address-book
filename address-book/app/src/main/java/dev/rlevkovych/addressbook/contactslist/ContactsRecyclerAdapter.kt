package dev.rlevkovych.addressbook.contactslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.rlevkovych.addressbook.R
import dev.rlevkovych.addressbook.data.entities.Contact
import kotlinx.android.synthetic.main.layout_contact_list_item.view.*

class ContactsRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items : List<Contact> = ArrayList()
    var onItemClick: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_contact_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ContactViewHolder -> {
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(contactList: List<Contact>) {
        items = contactList
        notifyDataSetChanged()
    }

    inner class ContactViewHolder constructor(
            itemView: View
    ): RecyclerView.ViewHolder(itemView) {
        val title = itemView.tv_label

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }

        fun bind(contact: Contact) {
            title.setText(contact.name);
        }
    }

}