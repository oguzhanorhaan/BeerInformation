package com.example.beerinformation.presentation.beerdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beerinformation.data.repository.BeersRepositoryImpl
import com.example.beerinformation.network.BeersApi
import com.example.beerinformation.presentation.beerlist.BeersApiStatus
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.datasource.remote.BeersRemoteDataSourceImpl
import com.example.beerinformation.domain.usecase.GetBeerListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class BeerDetailsViewModel(beerItem: Integer) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<BeersApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<BeersApiStatus>
        get() = _status

    private val _selectedItemId = MutableLiveData<Integer>()

    private val _selectedItem = MutableLiveData<BeerItemDTO>()

    val selectedItem: LiveData<BeerItemDTO>
        get() = _selectedItem


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        _selectedItemId.value = beerItem
        getSelectedBeerDetails()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getSelectedBeerDetails() {
        coroutineScope.launch {
            var getItemDetailsDeferred = BeersApi.retrofitService.getBeerItemDetails("beers/"+_selectedItemId.value.toString())
            try {
                _status.value = BeersApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val result = getItemDetailsDeferred.await()
                _status.value = BeersApiStatus.DONE
                _selectedItem.value = result[0]

            }catch (e: Exception) {
                _status.value = BeersApiStatus.ERROR
            }
        }
    }
}