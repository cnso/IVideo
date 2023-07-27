package org.jash.homepage.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.homepage.dao.VideoDao
import org.jash.mvicore.viewmodel.BaseViewModel

class DetailsViewModel(val videoDao: VideoDao):BaseViewModel<DetailsIntent, DetailsState>() {
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is DetailsIntent.LoadLocal -> loadVideo(it.id)
                }
            }
        }
    }
    fun loadVideo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = DetailsState.LocalResponse(videoDao.getVideoById(id))
        }
    }
}