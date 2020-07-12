package com.example.beerinformation

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.beerinformation.presentation.beerlist.BeerListAdapter
import com.example.beerinformation.presentation.beerlist.BeersApiStatus
import com.example.beerinformation.datasource.model.BeerItemDTO

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<BeerItemDTO>?) {
    val adapter = recyclerView.adapter as BeerListAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

/**
 * This binding adapter displays the [BeersApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("beersApiStatus")
fun bindStatus(statusImageView: ImageView, status: BeersApiStatus?) {
    when (status) {
        BeersApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        BeersApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        BeersApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}
