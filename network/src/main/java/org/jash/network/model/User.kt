package org.jash.network.model

data class User(
    val accountid: Int,
    val ctime: String,
    val headImg: String?,
    val id: Int,
    val isAuthor: Boolean?,
    val nick: String?,
    val password: String,
    val token: String?,
    val username: String
)