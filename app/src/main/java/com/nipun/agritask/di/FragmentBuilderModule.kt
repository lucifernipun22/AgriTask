package com.nipun.agritask.di

import com.nipun.agritask.di.addContact.AddContactViewModelModule
import com.nipun.agritask.di.contactDetails.ContactDetailsViewModelModule
import com.nipun.agritask.di.contactsList.ContactsListViewModelModule
import com.nipun.agritask.ui.addContact.AddContactFragment
import com.nipun.agritask.ui.contactDetails.ContactDetailsFragment
import com.nipun.agritask.ui.contactsList.ContactsListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [ContactsListViewModelModule::class])
    abstract fun contributeContactsFragment() : ContactsListFragment

    @ContributesAndroidInjector(modules = [AddContactViewModelModule::class])
    abstract fun contributeAddContactFragment() : AddContactFragment

    @ContributesAndroidInjector(modules = [ContactDetailsViewModelModule::class])
    abstract fun contributeContactDetailsFragment() : ContactDetailsFragment
}