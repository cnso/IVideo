package org.jash.homepage.net

import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel
import org.jash.network.model.ApiRes
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendSimpleVideo(@Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
    @GET("/videotype/getSimpleType")
    suspend fun getSimpleType(): ApiRes<List<SimpleType>>
    @GET("/videosimple/getSimpleVideoByChannelId")
    suspend fun getSimpleVideoByChannelId(@Query("channelId") channelId:String, @Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>

}