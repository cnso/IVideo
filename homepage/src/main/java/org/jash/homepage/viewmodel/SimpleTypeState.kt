package org.jash.homepage.viewmodel

import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel
import org.jash.mvicore.viewmodel.IState

sealed class SimpleTypeState:IState {
    data class LocalResponse(val data:List<VideoModel>):SimpleTypeState()
    data class RemoteResponse(val data:List<VideoModel>):SimpleTypeState()
    data class Error(val msg:String):SimpleTypeState()
}