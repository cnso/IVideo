package org.jash.homepage.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.jash.homepage.model.VideoModel

@Dao
interface VideoDao {
    @Upsert
    fun insert(vararg types: VideoModel)
    @Query("select * from videomodel")
    fun getAll():List<VideoModel>
    @Query("select * from videomodel where channelid = :channelId limit 0, :pageSize")
    fun getByChannelId(channelId: String, pageSize:Int):List<VideoModel>

}