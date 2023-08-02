package org.jash.homepage.model

import com.google.gson.annotations.JsonAdapter
import org.jash.homepage.net.DateDeserializer

data class Comment(
    val agreenum: Int = 0,
    val content: String,
    @JsonAdapter(DateDeserializer::class)
    val createtime: String = "",
    val datatype: Int,
    val id: Int = 0,
    val itemid: String,
    val nickname: String = "",
    val replyList: List<Comment> = listOf(),
    val replytotal: Int = 0,
    val userid: Int = 0,
    val userlogo: String = ""
)