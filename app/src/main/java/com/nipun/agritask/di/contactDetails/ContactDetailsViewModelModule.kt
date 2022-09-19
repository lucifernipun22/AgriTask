package com.nipun.agritask.di.contactDetails

import androidx.lifecycle.ViewModel
import com.nipun.agritask.di.ViewModelKey
import com.nipun.agritask.ui.contactDetails.ContactDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ContactDetailsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ContactDetailsViewModel::class)
    abstract fun bindContactDetailsViewModel(contactDetailsViewModel: ContactDetailsViewModel): ViewModel
}