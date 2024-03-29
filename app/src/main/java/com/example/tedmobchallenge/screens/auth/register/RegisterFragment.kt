package com.example.tedmobchallenge.screens.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tedmobchallenge.databinding.FragmentRegisterBinding
import com.example.tedmobchallenge.screens.auth.AuthViewModel
import com.example.tedmobchallenge.screens.auth.AuthViewModelFactory
import com.example.tedmobchallenge.utils.hideKeyboard

class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) { "You can only access the viewModel after onViewCreated()" }
        ViewModelProvider(this, AuthViewModelFactory(activity.application))[AuthViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater)

        binding.notYetRegister.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLogInFragment())
        }

        binding.buttonRegister.setOnClickListener {
            hideKeyboard(it)
            val tempUser =  binding.usernameRegister.text.toString().trimEnd()
            if (viewModel.doesUsernameExist(tempUser)) {
                Toast.makeText(requireContext(), "Username already exists! Log In", Toast.LENGTH_SHORT).show()
            }
            else {
                if (viewModel.isUsernameAcceptable(tempUser)) {
                    viewModel.saveUsername(tempUser)
                    findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToProductListFragment(tempUser))
                } else Toast.makeText(requireContext(), "Invalid Username! Check the requirements above.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.root.setOnClickListener {
            hideKeyboard(it)
        }

        return binding.root
    }

}