package org.jash.homepage.model

data class Comment(
    val agreenum: Int = 0,
    val content: String,
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