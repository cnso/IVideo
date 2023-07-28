package org.jash.homepage.viewmodel

import org.jash.homepage.model.BulletScreen
import org.jash.mvicore.viewmodel.IIntent

sealed class DetailsIntent:IIntent {
    data class LoadLocal(val id:Int):DetailsIntent()
    data class LoadBulletScreen(val itemid:String):DetailsIntent()
    data class SendBulletScreen(val bulletScreen: BulletScreen):DetailsIntent()
}