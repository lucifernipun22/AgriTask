package com.nipun.agritask.ui.contactsList

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nipun.agritask.data.Contact
import com.nipun.agritask.databinding.ListItemContactsBinding

class ContactsAdapter(val context: Context, val contactsClickListener: ContactsClickListener): ListAdapter<Contact, RecyclerView.ViewHolder>
    (ContactListDiffCallback()) {

    //region ON CREATE VIEW HOLDER
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ContactViewHolder(ListItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    //endregion


    //region ON BIND VIEW HOLDER
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentContact = getItem(position)

        (holder as ContactViewHolder).bind(contactsClickListener, currentContact)
    }
    //endregion

    class ContactViewHolder(private val binding: ListItemContactsBinding) :
        RecyclerView.ViewHolder(binding.root){


        fun bind(contactListener: ContactsClickListener, item: Contact){
            binding.apply {
                contactClickListener = contactListener
                contact = item
                executePendingBindings()
            }
        }
    }
}

private class ContactListDiffCallback : DiffUtil.ItemCallback<Contact>(){
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}

class ContactsClickListener(val clickListener: (contact: Contact) -> Unit) {
    fun onClick(contact: Contact) = clickListener(contact)
}