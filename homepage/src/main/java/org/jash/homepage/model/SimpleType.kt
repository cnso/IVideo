package org.jash.homepage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimpleType(
    val channelid: String,
    val haschild: Int,
    @PrimaryKey
    val id: Int,
    val pid: Int,
    val pinyin: String,
    val typename: String
)