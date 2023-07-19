package org.jash.homepage.net

import org.jash.homepage.model.VideoModel
import org.jash.network.model.ApiRes
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendSimpleVideo(@Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
}