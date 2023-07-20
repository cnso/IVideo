package org.jash.homepage.viewmodel

import org.jash.homepage.model.SimpleType
import org.jash.mvicore.viewmodel.IState

sealed class HomepageState:IState {
    data class LocalResponse(val data:List<SimpleType>):HomepageState()
    data class Response(val data:List<SimpleType>):HomepageState()
    data class Error(val msg:String):HomepageState()
}