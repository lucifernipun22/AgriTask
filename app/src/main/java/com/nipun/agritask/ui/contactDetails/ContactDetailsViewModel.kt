package com.nipun.agritask.ui.contactDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.agritask.data.Contact
import com.nipun.agritask.data.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ContactDetailsViewModel @Inject constructor(private val repository: Repository): ViewModel() {

    //region GTE CONTACT BY ID
    fun getContactByInt(contactId: Int) = repository.getContactById(contactId)
    //endregion

    //region DELETE REPO CONTACT
    private fun deleteRepositoryContact(contact: Contact?){
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }
    //endregion

    //region DELETE CONTACT
    fun deleteContact(contact: Contact?){
        deleteRepositoryContact(contact)
    }
    //endregion
}