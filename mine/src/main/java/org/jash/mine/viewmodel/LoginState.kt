package org.jash.mine.viewmodel

import org.jash.mvicore.viewmodel.IState
import org.jash.network.model.User

sealed class LoginState:IState {
    data class Error(val msg:String): LoginState()
    data class Response(val user:User): LoginState()
}