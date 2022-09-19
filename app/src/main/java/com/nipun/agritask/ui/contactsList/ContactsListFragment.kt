package com.nipun.agritask.ui.contactsList

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nipun.agritask.MainActivity

import com.nipun.agritask.R
import com.nipun.agritask.databinding.FragmentContactsListBinding
import com.nipun.agritask.di.ViewModelProviderFactory
import com.nipun.agritask.util.OPTIONS
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ContactsListFragment : DaggerFragment() {

    //region VARIABLES
    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory
    private lateinit var viewModel: ContactsListViewModel
    //endregion


    //region ON CREATE VIEW
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentContactsListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProviders.of(this, viewModelProviderFactory).get(ContactsListViewModel::class.java)

        val adapter = ContactsAdapter(context!!, ContactsClickListener {
            val id = it.id
            this.findNavController().navigate(ContactsListFragmentDirections
                .actionContactsListFragmentToContactDetailsFragment(id))
        })

        binding.contactList.adapter = adapter

        subscribeUI(adapter)

        binding.fab.setOnClickListener {
            it.findNavController().navigate(R.id.action_contactsListFragment_to_addContactFragment, null, OPTIONS)
        }

        return binding.root

    }
    //endregion

    //region SUBSCRIBE UI
    private fun subscribeUI(adapter: ContactsAdapter) {
        viewModel.getAllContacts.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }
    //endregion

    //region ON RESUME
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Agri Task"
    }
    //endregion
}
