package org.jash.homepage.viewmodel

import org.jash.homepage.model.VideoModel
import org.jash.mvicore.viewmodel.IState

sealed class HomepageState:IState {
    data class Response(val data:List<VideoModel>):HomepageState()
    data class Error(val msg:String):HomepageState()
}