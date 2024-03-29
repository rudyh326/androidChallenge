package com.example.tedmobchallenge.screens.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tedmobchallenge.databinding.FragmentLogInBinding
import com.example.tedmobchallenge.screens.auth.AuthViewModel
import com.example.tedmobchallenge.screens.auth.AuthViewModelFactory
import com.example.tedmobchallenge.utils.hideKeyboard

class LogInFragment : Fragment() {
    private lateinit var binding: FragmentLogInBinding

    private val viewModel: AuthViewModel by lazy {
        val activity = requireNotNull(this.activity) { "You can only access the viewModel after onViewCreated()" }
        ViewModelProvider(this, AuthViewModelFactory(activity.application))[AuthViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLogInBinding.inflate(inflater)

        binding.notYetLogin.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToRegisterFragment())
        }


        binding.buttonIn.setOnClickListener {
            hideKeyboard(it)
            val tempUser =  binding.usernameLogin.text.toString().trimEnd()
            if (viewModel.doesUsernameExist(tempUser)) {
                findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToProductListFragment(viewModel.currentUsername))
            }
            else Toast.makeText(requireContext(), "Username not found!", Toast.LENGTH_SHORT).show()
        }

        binding.root.setOnClickListener {
            hideKeyboard(it)
        }

        viewModel.storedUsernames.observe(viewLifecycleOwner){
            binding.buttonIn.isEnabled = !it.isNullOrEmpty()
        }

        return binding.root
    }

}