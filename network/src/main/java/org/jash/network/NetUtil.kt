package org.jash.network

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.jash.network.model.ApiRes
import org.jash.network.model.User
import org.json.JSONObject
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

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
val gson = GsonBuilder()
    .registerTypeAdapterFactory(StringSafeTypeAdapterFactory())
    .create()
val retrofit = Retrofit.Builder()
    .baseUrl(BuildConfig.HOST_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()