package com.example.beerinformation.presentation.beerdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beerinformation.datasource.model.mapToDomain
import com.example.beerinformation.domain.model.BeerItem
import com.example.beerinformation.data.datasource.BeersApiStatus
import com.example.beerinformation.domain.usecase.GetBeerDetailsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BeerDetailsViewModel constructor(private val beerItemId: Integer, private val getBeerDetailsUseCase: GetBeerDetailsUseCase) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<BeersApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<BeersApiStatus>
        get() = _status

    private val _selectedItemId = MutableLiveData<Integer>()

    private val _selectedItem = MutableLiveData<BeerItem>()

    val selectedItem: LiveData<BeerItem>
        get() = _selectedItem


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedItemId.value = beerItemId
        getSelectedBeerDetails()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getSelectedBeerDetails() {

        coroutineScope.launch {
            _status.value = BeersApiStatus.LOADING
            // this will run on a thread managed by Retrofit
            val result = getBeerDetailsUseCase.get(beerItemId.toString())
            _status.value = result.status

            when(_status.value) {
                BeersApiStatus.DONE -> {
                    val resItem  = result?.data?.get(0)
                    _selectedItem.value = resItem?.mapToDomain()
                }
            }
        }
    }
}