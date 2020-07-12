package com.example.beerinformation.presentation.beerlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.beerinformation.databinding.FragmentBeerListBinding

class BeerListFragment : Fragment() {

    private val viewModel: BeerListViewModel by lazy {
        ViewModelProviders.of(this).get(BeerListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentBeerListBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        binding.beerList.adapter = BeerListAdapter(BeerListAdapter.OnClickListener {
            viewModel.displayItemDetails(it)
        })

        // Observe the navigateToSelectedProperty LiveData and Navigate when it isn't null
        // After navigating, call displayPropertyDetailsComplete() so that the ViewModel is ready
        // for another navigation event.
        viewModel.navigateToSelectedItem.observe(this, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(BeerListFragmentDirections.actionBeerListFragmentToBeerDetailsFragment(it.id.toInt()))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })


        setHasOptionsMenu(true)
        return binding.root
    }

}