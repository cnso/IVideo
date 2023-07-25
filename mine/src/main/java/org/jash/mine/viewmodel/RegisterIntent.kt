package org.jash.mine.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class RegisterIntent:IIntent {
    data class Register(val username:String, val password:String): RegisterIntent()
}