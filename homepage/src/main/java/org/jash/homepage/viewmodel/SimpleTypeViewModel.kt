package org.jash.homepage.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.homepage.dao.VideoDao
import org.jash.homepage.net.HomeService
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.retrofit
import java.lang.Exception

class SimpleTypeViewModel(val dao: VideoDao):BaseViewModel<SimpleTypeIntent, SimpleTypeState>() {
    val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is SimpleTypeIntent.GetLocalVideo -> getVideo(it.channelId)
                    is SimpleTypeIntent.GetRemoteVideo -> getRemoteVideo(it.channelId, it.page)
                }
            }
        }
    }
    fun getVideo(channelId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = SimpleTypeState.LocalResponse(dao.getByChannelId(channelId, 10))
        }
    }
    fun getRemoteVideo(channelId: String, page:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = try {
                val res = service.getSimpleVideoByChannelId(channelId, page, 10)
                if (res.code == 0) {
                    dao.insert(*res.data.toTypedArray())
                    SimpleTypeState.RemoteResponse(res.data)

                } else {
                    SimpleTypeState.Error(res.msg)
                }
            } catch (e:Exception) {
                SimpleTypeState.Error(e.message ?: "网络错误")
            }
        }
    }
}