package com.example.beerinformation

import com.example.beerinformation.data.datasource.BeersRemoteDataSource
import com.example.beerinformation.data.repository.BeersRepositoryImpl
import com.example.beerinformation.datasource.remote.BeerApiService
import com.example.beerinformation.datasource.remote.BeersRemoteDataSourceImpl
import com.example.beerinformation.datasource.remote.createNetworkClient
import com.example.beerinformation.domain.repository.BeersRepository
import com.example.beerinformation.domain.usecase.GetBeerDetailsUseCase
import com.example.beerinformation.domain.usecase.GetBeerListUseCase
import com.example.beerinformation.presentation.beerdetails.BeerDetailsViewModel
import com.example.beerinformation.presentation.beerlist.BeerListViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import org.koin.android.viewmodel.dsl.viewModel


fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules( listOf(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule
    ))
}

val viewModelModule: Module = module {
    viewModel { BeerListViewModel(getBeerListUseCase = get()) }
    viewModel { (id : Integer) -> BeerDetailsViewModel(id, getBeerDetailsUseCase = get ()) }
}

val useCaseModule: Module = module {
    factory { GetBeerListUseCase(beersRepository = get()) }
    factory { GetBeerDetailsUseCase(beersRepository = get()) }
}

val repositoryModule: Module = module {
    single { BeersRepositoryImpl( remoteDataSource = get()) as BeersRepository }
}

val dataSourceModule: Module = module {
    single { BeersRemoteDataSourceImpl(api = beersApi) as BeersRemoteDataSource  }
}

val networkModule: Module = module {
    single { beersApi }
}


private const val BASE_URL = "https://api.punkapi.com/v2/"

private val retrofit: Retrofit = createNetworkClient(BASE_URL)

private val beersApi: BeerApiService = retrofit.create(BeerApiService::class.java)