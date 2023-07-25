package org.jash.mine.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.UserService
import org.jash.network.model.User
import org.jash.network.retrofit

class RegisterViewModel:BaseViewModel<RegisterIntent, RegisterState>() {
    val service = retrofit.create(UserService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is RegisterIntent.Register -> register(it.username, it.password)
                }
            }
        }


    }

    fun register(username:String, password: String) {
        viewModelScope.launch {
            state.value = try {
                val res = service.register(username, password)
                if (res.code == 0) {
                    RegisterState.Response(res.data)
                } else {
                    RegisterState.Error(res.msg)
                }
            } catch (e:Exception) {
                RegisterState.Error(e.message ?: "")
            }
        }

    }
}