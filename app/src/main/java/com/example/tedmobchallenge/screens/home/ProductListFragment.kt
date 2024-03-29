package com.example.tedmobchallenge.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.tedmobchallenge.R
import com.example.tedmobchallenge.databinding.FragmentProductListBinding

class ProductListFragment : Fragment() {
    private lateinit var binding: FragmentProductListBinding

    private val viewModel: ProductListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this,ProductListViewModelFactory(activity.application))[ProductListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProductListBinding.inflate(inflater)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        val currentUsername = ProductListFragmentArgs.fromBundle(requireArguments()).username
        binding.username.text = context?.getString(R.string.user)?.plus(": ").plus(currentUsername)

        binding.productList.adapter = ProductListAdapter(ProductListAdapter.OnClickListener {
            viewModel.displayProductDetails(it)
        })

        binding.showMapBtn.setOnClickListener {
            this.findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToMapFragment2())
        }

        viewModel.navigateToSelectedProduct.observe(viewLifecycleOwner) {
            if (null != it) {
                this.findNavController().navigate(ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(it))
                viewModel.displayProductDetailsComplete()
            }
        }

        return binding.root
    }


}