package org.jash.homepage.net

import org.jash.homepage.model.BulletScreen
import org.jash.homepage.model.Comment
import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel
import org.jash.network.model.ApiRes
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface HomeService {
    @GET("/videosimple/getRecommendSimpleVideo")
    suspend fun getRecommendSimpleVideo(@Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
    @GET("/videotype/getSimpleType")
    suspend fun getSimpleType(): ApiRes<List<SimpleType>>
    @GET("/videosimple/getSimpleVideoByChannelId")
    suspend fun getSimpleVideoByChannelId(@Query("channelId") channelId:String, @Query("page") page:Int, @Query("pagesize") pagesize:Int): ApiRes<List<VideoModel>>
    @GET("/bulletscreen/getBulletScreenInfo")
    suspend fun getBulletScreenInfo(@Query("datatype") datatype:Int, @Query("itemid") itemid:String):ApiRes<List<BulletScreen>>
    @GET("/comment/getCommentByUserId")
    suspend fun getCommentByItemId(@Query("datatype") datatype:Int, @Query("itemid") itemid:String):ApiRes<List<Comment>>
    @POST("/comment/publishComment")
    suspend fun publishComment(@Body comment: Comment):ApiRes<Comment>
    @POST("/bulletscreen/publishBulletScreen")
    suspend fun sendBulletScreen(@Body bulletScreen: BulletScreen):ApiRes<BulletScreen>
}