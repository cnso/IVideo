package org.jash.mine.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.UserService
import org.jash.network.model.User
import org.jash.network.retrofit

class LoginViewModel:BaseViewModel<LoginIntent, LoginState>() {
    val service = retrofit.create(UserService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is LoginIntent.Login -> login(it.username, it.password)
                }
            }
        }


    }

    fun login(username:String, password: String) {
        viewModelScope.launch {
            state.value = try {
                val res = service.login(username, password)
                if (res.code == 0) {
                    LoginState.Response(res.data)
                } else {
                    LoginState.Error(res.msg)
                }
            } catch (e:Exception) {
                e.printStackTrace()
                LoginState.Error(e.message ?: "")
            }
        }

    }
}