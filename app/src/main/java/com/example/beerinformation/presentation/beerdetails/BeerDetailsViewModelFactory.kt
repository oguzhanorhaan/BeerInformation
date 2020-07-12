package com.example.beerinformation.presentation.beerdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BeerDetailsViewModelFactory (
    private val beerItem: Integer
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BeerDetailsViewModel::class.java)) {
            return BeerDetailsViewModel(beerItem) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}