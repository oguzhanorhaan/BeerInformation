package com.example.beerinformation.network

import com.example.beerinformation.datasource.model.BeerItemDTO
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private const val BASE_URL = "https://api.punkapi.com/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface BeerApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [BeerItemDTO] which can be fetched with await() if
     * in a Coroutine scope.
     */
    @GET("beers")
    fun getBeerItems():
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<List<BeerItemDTO>>

    @GET
    fun getBeerItemDetails(@Url url : String):
            Deferred<List<BeerItemDTO>>
}


object BeersApi {
    val retrofitService : BeerApiService by lazy { retrofit.create(BeerApiService::class.java) }
}