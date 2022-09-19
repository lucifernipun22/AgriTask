package com.nipun.agritask.di.contactsList

import androidx.lifecycle.ViewModel
import com.nipun.agritask.di.ViewModelKey
import com.nipun.agritask.ui.contactsList.ContactsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactsListViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactsListViewModel::class)
    abstract fun bindContactsListViewModel(contactsListViewModel: ContactsListViewModel): ViewModel

}