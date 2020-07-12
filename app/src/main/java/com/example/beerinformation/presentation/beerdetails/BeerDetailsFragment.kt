package com.example.beerinformation.presentation.beerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.beerinformation.databinding.FragmentBeerDetailsBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class BeerDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentBeerDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val beerItem = BeerDetailsFragmentArgs.fromBundle(arguments!!).selectedItem
        val vm: BeerDetailsViewModel by inject<BeerDetailsViewModel> { parametersOf(Integer(beerItem)) }
        binding.viewModel = vm
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
}