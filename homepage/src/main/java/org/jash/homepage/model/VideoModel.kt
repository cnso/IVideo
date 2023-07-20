package org.jash.homepage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VideoModel(
    val avatar_url: String,
    val channelid: String,
    val commentnum: Int,
    val ctime: String,
    val description: String,
    val group_id: String,
    @PrimaryKey
    val id: Int,
    val image_url: String,
    val item_id: String,
    val name: String,
    val playnum: Int,
    val preview_url: String,
    val publish_time: String?,
    val title: String,
    val userid: String,
    val verifycode: String,
    val videomainimag: String,
    val videopath: String
)