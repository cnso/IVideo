package org.jash.ivideo.viewmodel

import org.jash.ivideo.model.VideoModel
import org.jash.mvicore.viewmodel.IState

sealed class MainState:IState {
    data class Progress(val v:Int):MainState()
    data class Finish(val s:String) :MainState()
}