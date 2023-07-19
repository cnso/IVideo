package org.jash.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.HOST_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()