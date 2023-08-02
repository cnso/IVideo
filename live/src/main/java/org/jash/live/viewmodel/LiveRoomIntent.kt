package org.jash.live.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class LiveRoomIntent:IIntent {
    data object LoadRoom:LiveRoomIntent()
}