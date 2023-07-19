package org.jash.mvicore.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel<INTENT:IIntent, STATE:IState>:ViewModel() {
    val intent:Channel<INTENT> by lazy { Channel(Channel.UNLIMITED) }
    val state: MutableStateFlow<STATE?> by lazy { MutableStateFlow(null) }
}