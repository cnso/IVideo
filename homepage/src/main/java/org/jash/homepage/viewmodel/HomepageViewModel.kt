package org.jash.homepage.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.homepage.dao.SimpleTypeDao
import org.jash.homepage.net.HomeService
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.retrofit

class HomepageViewModel(val dao: SimpleTypeDao):BaseViewModel<HomepageIntent, HomepageState>() {
    val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    HomepageIntent.LoadType -> loadType()
                }
            }
        }
    }
    fun loadType() {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = HomepageState.LocalResponse(dao.getAll())
            state.value = try {
                val res = service.getSimpleType()
                if (res.code == 0) {
                    dao.insert(*res.data.toTypedArray())
                    HomepageState.Response(res.data)
                } else {
                    HomepageState.Error(res.msg)
                }
            } catch (e: Exception) {
                HomepageState.Error(e.message ?: "")
            }
        }
    }
}