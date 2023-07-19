package org.jash.network.model

data class ApiRes<T> (
    val code:Int,
    val msg: String,
    val data: T
)