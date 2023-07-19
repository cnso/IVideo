package org.jash.ivideo.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class MainIntent:IIntent {
    data class Start(val count:Int):MainIntent()
}