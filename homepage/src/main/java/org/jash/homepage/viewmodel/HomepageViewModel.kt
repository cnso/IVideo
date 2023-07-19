package org.jash.homepage.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.homepage.net.HomeService
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.retrofit

class HomepageViewModel:BaseViewModel<HomepageIntent, HomepageState>() {
    val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is HomepageIntent.LoadPage -> loadPage(it)
                }
            }
        }
    }
    fun loadPage(loadPage: HomepageIntent.LoadPage) {
        viewModelScope.launch {
            state.value = try {
                val res = service.getRecommendSimpleVideo(loadPage.page, 10)
                if (res.code == 0) {
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