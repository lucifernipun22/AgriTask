package com.nipun.agritask.ui.addContact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nipun.agritask.data.Contact
import com.nipun.agritask.data.Repository
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddContactViewModel @Inject constructor(private val repository: Repository): ViewModel()  {

    var firstName = ""
    var lastName = ""
    var phoneNumber = ""
    var email = ""

    //region UPDATE REPO CONTACT
    private fun updateRepositoryContact(contact: Contact){
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }
    //endregion

    //region UPDATE CONTACT
    fun updateContact(contact: Contact){
        updateRepositoryContact(contact)
    }
    //endregion

    //region SAVE CONTACT
    fun saveContact(contact: Contact){
        insertContact(contact)
    }
    //endregion

    //region INSERT CONTACT
    private fun insertContact(contact: Contact){
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }
    //endregion

    //region GET CONTACT BY ID
    fun getContactById(contactId: Int) = repository.getContactById(contactId)
    //endregion
}