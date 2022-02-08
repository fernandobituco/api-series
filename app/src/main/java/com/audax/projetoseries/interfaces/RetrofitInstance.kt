package com.audax.projetoseries.interfaces

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: SeriesAPI by lazy {
        Retrofit.Builder()
                .baseUrl("https://series.audax.mobi/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SeriesAPI::class.java)
    }
}