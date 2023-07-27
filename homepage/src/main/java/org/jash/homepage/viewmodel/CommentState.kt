package org.jash.homepage.viewmodel

import org.jash.homepage.model.Comment
import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel
import org.jash.mvicore.viewmodel.IState

sealed class CommentState:IState {
    data class LocalResponse(val data:VideoModel):CommentState()
    data class CommentResponse(val data:List<Comment>):CommentState()
    data class PublishResponse(val data:Comment):CommentState()
    data class Error(val msg:String):CommentState()
}