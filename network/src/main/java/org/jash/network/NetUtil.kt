package org.jash.network

import okhttp3.OkHttpClient
import org.jash.network.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var user: User? = null
private val client by lazy {
    OkHttpClient.Builder()
        .addInterceptor {
            if (user != null) {
                it.proceed(it.request().newBuilder().header("token", user?.token).build())
            } else {
                it.proceed(it.request())
            }
        }
        .build()
}
val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.HOST_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()