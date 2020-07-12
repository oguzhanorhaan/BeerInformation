package com.example.beerinformation.domain.model

import com.squareup.moshi.Json

data class BeerItem(
    val id: Integer,
    val name: String,
    val tagline: String,
    val description: String,
    val breweryTips: String,
    val imgSrcUrl: String
) {
}