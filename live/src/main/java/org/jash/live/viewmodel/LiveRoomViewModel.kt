package org.jash.live.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.live.model.LiveRoom
import org.jash.mvicore.viewmodel.BaseViewModel

class LiveRoomViewModel:BaseViewModel<LiveRoomIntent, LiveRoomState>() {
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    LiveRoomIntent.LoadRoom -> loadRoom();
                }
            }
        }
    }
    fun loadRoom() {
        viewModelScope.launch {
            state.value = LiveRoomState.RoomRespose(listOf(
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
            ))
        }
    }
}