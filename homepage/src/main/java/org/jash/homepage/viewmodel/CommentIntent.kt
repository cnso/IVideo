package org.jash.homepage.viewmodel

import org.jash.homepage.model.Comment
import org.jash.mvicore.viewmodel.IIntent

sealed class CommentIntent:IIntent {
    data class LoadLocal(val id:Int):CommentIntent()
    data class LoadComment(val itemId:String):CommentIntent()
    data class PublishComment(val comment:Comment):CommentIntent()
}