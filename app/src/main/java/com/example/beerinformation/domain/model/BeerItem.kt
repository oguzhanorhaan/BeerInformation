package com.example.beerinformation.domain.model

data class BeerItem(
    val id: Integer,
    val name: String,
    val tagline: String,
    val description: String,
    val breweryTips: String,
    val imgSrcUrl: String
) {
}