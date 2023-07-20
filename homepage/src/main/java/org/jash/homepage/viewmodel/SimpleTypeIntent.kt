package org.jash.homepage.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class SimpleTypeIntent:IIntent {
    data class GetLocalVideo(val channelId:String):SimpleTypeIntent()
    data class GetRemoteVideo(val channelId:String, val page:Int):SimpleTypeIntent()
}