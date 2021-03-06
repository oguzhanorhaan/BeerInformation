package com.example.beerinformation.presentation.beerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beerinformation.datasource.model.mapToDomain
import com.example.beerinformation.domain.model.BeerItem
import com.example.beerinformation.data.model.BeersApiStatus
import com.example.beerinformation.domain.usecase.GetBeerListUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class BeerListViewModel constructor(private val getBeerListUseCase: GetBeerListUseCase): ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<BeersApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<BeersApiStatus>
        get() = _status


    private val _items = MutableLiveData<List<BeerItem>>()


    val items: LiveData<List<BeerItem>>
        get() = _items


    private val _navigateToSelectedItem = MutableLiveData<BeerItem>()


    val navigateToSelectedItem: LiveData<BeerItem>
        get() = _navigateToSelectedItem

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getBeerListItems()
    }

    private fun getBeerListItems() {
        coroutineScope.launch {
            // Get the Deferred object for our Retrofit request
            _status.value = BeersApiStatus.LOADING
            // this will run on a thread managed by Retrofit
            val listResult = getBeerListUseCase.get()
            _status.value = listResult.status

            when(_status.value) {
                BeersApiStatus.DONE -> {
                    var domainItems = ArrayList<BeerItem>()
                    listResult.data?.forEach{
                        domainItems.add(it.mapToDomain())
                    }
                    _items.value = domainItems
                }

                BeersApiStatus.ERROR ->_items.value = ArrayList()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param beerItem The [BeerItem] that was clicked on.
     */
    fun displayItemDetails(beerItem: BeerItem) {
        _navigateToSelectedItem.value = beerItem
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayItemDetailsComplete() {
        _navigateToSelectedItem.value = null
    }

}