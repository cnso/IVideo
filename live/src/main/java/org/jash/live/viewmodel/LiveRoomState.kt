package org.jash.live.viewmodel

import org.jash.live.model.LiveRoom
import org.jash.mvicore.viewmodel.IState

sealed class LiveRoomState:IState {
    data class RoomRespose(val data:List<LiveRoom>):LiveRoomState()
    data class Error(val msg:String):LiveRoomState()
}