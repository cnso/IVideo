package org.jash.mine.viewmodel

import org.jash.mvicore.viewmodel.IState
import org.jash.network.model.User

sealed class RegisterState:IState {
    data class Error(val msg:String): RegisterState()
    data class Response(val user:User): RegisterState()
}