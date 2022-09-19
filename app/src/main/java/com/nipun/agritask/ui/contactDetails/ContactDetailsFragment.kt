package com.nipun.agritask.ui.contactDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nipun.agritask.MainActivity
import com.nipun.agritask.R
import com.nipun.agritask.databinding.FragmentContactDetailsBinding
import com.nipun.agritask.di.ViewModelProviderFactory
import com.nipun.agritask.util.OPTIONS
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ContactDetailsFragment : DaggerFragment() {

    //region VARIABLES
    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: ContactDetailsViewModel
    //endregion

    //region ON CREATE VIEW
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val binding: FragmentContactDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner

        viewModel = ViewModelProviders.of(this, factory).get(ContactDetailsViewModel::class.java)

        val args = ContactDetailsFragmentArgs.fromBundle(arguments!!)
        val contact = viewModel.getContactByInt(args.id)

        contact.observe(this, Observer {
            binding.data.contact = it
        })

        binding.editContactFab.setOnClickListener {
            it.findNavController().navigate(
                ContactDetailsFragmentDirections
                    .actionContactDetailsFragmentToAddContactFragment(args.id)
            )
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteContact(binding.data.contact)
            it.findNavController().navigate(
                ContactDetailsFragmentDirections
                    .actionContactDetailsFragmentToContactsListFragment()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(this)
        {
            // handling back button
            findNavController().navigate(ContactDetailsFragmentDirections.actionContactDetailsFragmentToContactsListFragment(), OPTIONS)
        }

        return binding.root
    }
    //endregion

    //region ON RESUME
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Contact Details"
    }
    //endregion

}