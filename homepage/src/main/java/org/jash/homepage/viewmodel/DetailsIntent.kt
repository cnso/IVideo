package org.jash.homepage.viewmodel

import org.jash.mvicore.viewmodel.IIntent

sealed class DetailsIntent:IIntent {
    data class LoadLocal(val id:Int):DetailsIntent()
}