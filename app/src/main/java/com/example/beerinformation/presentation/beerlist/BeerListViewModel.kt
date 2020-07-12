package com.example.beerinformation.presentation.beerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beerinformation.data.repository.BeersRepositoryImpl
import com.example.beerinformation.network.BeersApi
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.datasource.remote.BeersRemoteDataSourceImpl
import com.example.beerinformation.domain.usecase.GetBeerListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class BeersApiStatus { LOADING, ERROR, DONE }


class BeerListViewModel: ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<BeersApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<BeersApiStatus>
        get() = _status


    private val _items = MutableLiveData<List<BeerItemDTO>>()


    val items: LiveData<List<BeerItemDTO>>
        get() = _items


    private val _navigateToSelectedItem = MutableLiveData<BeerItemDTO>()


    val navigateToSelectedItem: LiveData<BeerItemDTO>
        get() = _navigateToSelectedItem

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getBeerListItems()
    }

    private fun getBeerListItems() {
        /*coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            var getItemsDeferred = BeersApi.retrofitService.getBeerItems()
            try {
                _status.value = BeersApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getItemsDeferred.await()
                _status.value = BeersApiStatus.DONE
                _items.value = listResult
            } catch (e: Exception) {
                _status.value = BeersApiStatus.ERROR
                _items.value = ArrayList()
            }
        } */

        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            try {
                _status.value = BeersApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val uc = GetBeerListUseCase(BeersRepositoryImpl(BeersRemoteDataSourceImpl(com.example.beerinformation.datasource.remote.BeersApi.retrofitService)))
                val listResult= uc.getBeerList()
                _status.value = BeersApiStatus.DONE
                _items.value = listResult
            } catch (e: Exception) {
                _status.value = BeersApiStatus.ERROR
                _items.value = ArrayList()
            }
        }


    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param beerItem The [BeerItemDTO] that was clicked on.
     */
    fun displayItemDetails(beerItem: BeerItemDTO) {
        _navigateToSelectedItem.value = beerItem
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedItem.value = null
    }

}