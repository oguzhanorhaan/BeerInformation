package com.example.beerinformation.datasource.model

import android.os.Parcelable
import com.example.beerinformation.domain.model.BeerItem
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BeerItemDTO(
    val id: Integer,
    val name: String,
    val tagline: String,
    val description: String,
    @Json(name = "brewers_tips")  val breweryTips: String,
    // used to map img_src from the JSON to imgSrcUrl in our class
    @Json(name = "image_url") val imgSrcUrl: String) : Parcelable {

}

fun BeerItemDTO.mapToDomain(): BeerItem = BeerItem(id, name, tagline, description, breweryTips, imgSrcUrl)