package com.example.beerinformation.presentation.beerlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beerinformation.databinding.ListViewItemBinding
import com.example.beerinformation.domain.model.BeerItem


class BeerListAdapter( val onClickListener: OnClickListener ) :
    ListAdapter<BeerItem, BeerListAdapter.BeerItemViewHolder>(DiffCallback) {

    class BeerItemViewHolder(private var binding: ListViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(beerItem: BeerItem) {
            binding.property = beerItem
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<BeerItem>() {
        override fun areItemsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: BeerItem, newItem: BeerItem): Boolean {
            return oldItem.id == newItem.id
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): BeerItemViewHolder {
        return BeerItemViewHolder(ListViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: BeerItemViewHolder, position: Int) {
        val beerItem = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(beerItem)
        }
        holder.bind(beerItem)
    }


    class OnClickListener(val clickListener: (beerItem: BeerItem) -> Unit) {
        fun onClick(beerItem: BeerItem) = clickListener(beerItem)
    }
}