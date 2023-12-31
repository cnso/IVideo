package org.jash.homepage.viewmodel

import org.jash.homepage.model.BulletScreen
import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel
import org.jash.mvicore.viewmodel.IState

sealed class DetailsState:IState {
    data class LocalResponse(val data:VideoModel):DetailsState()
    data class BulletScreenResponse(val data: List<BulletScreen>):DetailsState();
    data class SendResponse(val data: BulletScreen):DetailsState();
    data class Error(val msg:String):DetailsState()
}