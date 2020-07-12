package com.example.beerinformation.presentation.beerdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.beerinformation.databinding.FragmentBeerDetailsBinding


class BeerDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentBeerDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val beerItem = BeerDetailsFragmentArgs.fromBundle(arguments!!).selectedItem
        val viewModelFactory = BeerDetailsViewModelFactory(Integer(beerItem))
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory).get(BeerDetailsViewModel::class.java)
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