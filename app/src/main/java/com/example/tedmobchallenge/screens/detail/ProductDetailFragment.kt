package com.example.tedmobchallenge.screens.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.tedmobchallenge.databinding.FragmentProductDetailBinding

class ProductDetailFragment : DialogFragment() {
    private lateinit var binding: FragmentProductDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductDetailBinding.inflate(inflater)

        val application = requireNotNull(activity).application
        binding.lifecycleOwner = this

        val parcelableProduct = ProductDetailFragmentArgs.fromBundle(requireArguments()).selectedProduct

        val viewModelFactory = DetailProductViewModelFactory(parcelableProduct, application)

        val viewModel = ViewModelProvider(this, viewModelFactory)[ProductDetailViewModel::class.java]

        binding.viewModel = viewModel

        parentFragment?.view?.alpha = 0.3f

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        parentFragment?.view?.alpha = 1.0f
    }

}