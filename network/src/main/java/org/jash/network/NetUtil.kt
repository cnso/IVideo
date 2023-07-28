package org.jash.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
val gson: Gson = GsonBuilder()
    .registerTypeAdapterFactory(StringSafeTypeAdapterFactory())
    .create()
val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.HOST_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()