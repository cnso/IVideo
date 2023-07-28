package org.jash.homepage.model

data class BulletScreen(
    val content: String,
    val createtime: String = "",
    val datatype: Int,
    val fontcolor: String = "",
    val fontsize: Int = 0,
    val id: Int = 0,
    val itemid: String,
    val playtime: String = "",
    val userid: Int = 0
)