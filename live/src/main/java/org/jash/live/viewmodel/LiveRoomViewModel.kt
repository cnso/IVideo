package org.jash.live.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.live.model.LiveRoom
import org.jash.live.xmpp.XMPPManager
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
        viewModelScope.launch(Dispatchers.IO) {
//            state.value = LiveRoomState.RoomRespose(listOf(
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//                LiveRoom("tony", "https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?"),
//            ))
            state.value = try {
                LiveRoomState.RoomRespose(XMPPManager.getChatRooms()
                    ?.map { LiveRoom(it,"https://img-blog.csdnimg.cn/20201014180756928.png", "办卡吗?") } ?: listOf())
            } catch (e:Exception) {
                LiveRoomState.Error(e.message ?: "")
            }
        }
    }
}