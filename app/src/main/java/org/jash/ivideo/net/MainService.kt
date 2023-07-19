package org.jash.ivideo.net

import org.jash.ivideo.model.VideoModel
import org.jash.network.model.ApiRes
import retrofit2.http.GET

interface MainService {
    @GET("/videosimple/getRecommendSimpleVideo?page=1&pagesize=1")
    suspend fun getRecommendSimpleVideo():ApiRes<List<VideoModel>>
}