package org.jash.mine.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class LoginIntent:IIntent {
    data class Login(val username:String, val password:String): LoginIntent()
}