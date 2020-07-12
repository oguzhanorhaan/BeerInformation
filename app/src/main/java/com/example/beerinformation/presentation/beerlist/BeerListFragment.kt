package com.example.beerinformation.presentation.beerlist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.beerinformation.databinding.FragmentBeerListBinding
import com.example.beerinformation.injectFeature
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import org.koin.android.ext.android.inject

class BeerListFragment : Fragment() {

    private val viewModel by inject<BeerListViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentBeerListBinding.inflate(inflater)

        injectFeature()

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.beerList.addItemDecoration(  LayoutMarginDecoration( 1, 15 ) );

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
                viewModel.displayItemDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

}