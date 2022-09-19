package com.nipun.agritask.ui.addContact


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.nipun.agritask.MainActivity

import com.nipun.agritask.R
import com.nipun.agritask.data.Contact
import com.nipun.agritask.databinding.FragmentAddContactBinding
import com.nipun.agritask.di.ViewModelProviderFactory
import com.nipun.agritask.util.OPTIONS
import dagger.android.support.DaggerFragment
import timber.log.Timber
import javax.inject.Inject

class AddContactFragment : DaggerFragment() {

    //region VARIABLES
    private val REQUEST_IMAGE = 100
    private var profilePicturePath: String? = null

    private lateinit var binding: FragmentAddContactBinding

    @Inject
    lateinit var factory: ViewModelProviderFactory

    private lateinit var viewModel: AddContactViewModel
    //endregion

    //region ONCREATE VIEW
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_contact, container, false
        )

        binding.lifecycleOwner = this.viewLifecycleOwner

        viewModel = ViewModelProviders.of(this, factory).get(AddContactViewModel::class.java)

        val args = AddContactFragmentArgs.fromBundle(arguments!!)

        if (args.id != -1) {
            val contact = viewModel.getContactById(args.id)
            contact.observe(this, Observer {
                binding.contact = it
            })
        }

        binding.firstNameEditText.doAfterTextChanged {
            viewModel.firstName = it.toString()
        }

        binding.lastNameEditText.doAfterTextChanged {
            viewModel.lastName = it.toString()
        }

        binding.phoneNumberEditText.doAfterTextChanged {
            viewModel.phoneNumber = it.toString()
        }

        binding.emailEditText.doAfterTextChanged {
            viewModel.email = it.toString()
        }

        binding.uploadProfilePictureImageView.setOnClickListener {
            selectProfilePicture()
        }


        binding.saveButton.setOnClickListener {
            if (validation()) {
                if (args.id == -1) {
                    Timber.d("Hello this contact name is ${viewModel.firstName} ${viewModel.lastName}")
                    viewModel.saveContact(
                        Contact(
                            viewModel.firstName,
                            viewModel.lastName,
                            viewModel.phoneNumber,
                            viewModel.email,
                            profilePicturePath
                        )
                    )
                    it.findNavController().navigate(
                        AddContactFragmentDirections
                            .actionAddContactFragmentToContactsFragment()
                    )
                } else {
                    Timber.d("Hello this contact name is ${viewModel.firstName}")
                    viewModel.updateContact(
                        Contact(
                            viewModel.firstName,
                            viewModel.lastName,
                            viewModel.phoneNumber,
                            viewModel.email,
                            profilePicturePath,
                            args.id
                        )
                    )
                    Timber.d("Hello image is $profilePicturePath")

                    it.findNavController().navigate(
                        AddContactFragmentDirections
                            .actionAddContactFragmentToContactsFragment()
                    )
                }
            }
        }

        binding.cancelButton.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this)
        {
            // handling back button
            findNavController().navigate(
                AddContactFragmentDirections.actionAddContactFragmentToContactsFragment(),
                OPTIONS
            )
        }

        return binding.root
    }
    //endregion

    //region VALIDATION
    private fun validation(): Boolean {
        val firstName = binding.firstNameEditText.text.toString()
        val lastName = binding.lastNameEditText.text.toString()
        val phone = binding.phoneNumberEditText.text.toString()
        val email = binding.emailEditText.text.toString()

        if (firstName.isBlank() && lastName.isBlank() && phone.isBlank() && email.isBlank()) {
            Toast.makeText(requireContext(), "Please Enter the all details", Toast.LENGTH_SHORT)
                .show()
            return false
        }
        return true


    }
    //endregion

    //region SELECT PROFILE PICTURE
    private fun selectProfilePicture() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivityForResult(intent, REQUEST_IMAGE)
        }
    }
    //endregion

    //region ON ACTIVITY RESULT
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            Timber.d("URI $imageUri")

            profilePicturePath = imageUri.toString()
            Timber.d("image path ***** $profilePicturePath")
            binding.uploadProfilePictureImageView.setImageURI(data?.data)
        }
    }
    //endregion

    //region ON RESUME
    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title = "Add New Contact"
    }
    //endregion
}
