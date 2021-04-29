package ru.jsavings.data.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.jsavings.data.network.crypto.CryptoApi

private const val BASE_URL = "api.coingecko.com/api/v3"

internal val networkModule = module {

	factory { GsonConverterFactory.create() }

	single {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(get())
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.build()
	}

	single { get<Retrofit>().create(CryptoApi::class.java) }
}