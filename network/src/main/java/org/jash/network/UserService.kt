package org.jash.network

import org.jash.network.model.ApiRes
import org.jash.network.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    @POST("/user/login")
    @FormUrlEncoded
    fun login(@Field("username") username:String, @Field("password") password:String):ApiRes<User>
    @POST("/user/register")
    @FormUrlEncoded
    fun register(@Field("username") username:String, @Field("password") password:String):ApiRes<User>
}