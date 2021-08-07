package ru.jsavings.data.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.jsavings.data.network.api.exchangerate.ExchangeRateApi

private const val BASE_URL = "https://api.exchangerate.host/"

internal val networkModule = module {

	single {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.client(
				OkHttpClient.Builder()
					.addInterceptor(HttpLoggingInterceptor().apply {
						level = HttpLoggingInterceptor.Level.BODY
					})
					.build()
			)
			.build()
	}

	single { get<Retrofit>().create(ExchangeRateApi::class.java) }
}