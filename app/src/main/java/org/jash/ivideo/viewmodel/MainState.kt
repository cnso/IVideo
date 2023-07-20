package org.jash.ivideo.viewmodel

import org.jash.mvicore.viewmodel.IState

sealed class MainState:IState {
    data class Progress(val v:Int):MainState()
    data class Finish(val s:String) :MainState()
}