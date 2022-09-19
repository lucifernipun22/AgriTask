package com.nipun.agritask.di.addContact

import androidx.lifecycle.ViewModel
import com.nipun.agritask.di.ViewModelKey
import com.nipun.agritask.ui.addContact.AddContactViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddContactViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AddContactViewModel::class)
    abstract fun bindAddContactViewModel(addContactViewModel: AddContactViewModel): ViewModel

}