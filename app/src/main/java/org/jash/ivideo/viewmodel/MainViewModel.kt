package org.jash.ivideo.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.jash.ivideo.net.MainService
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.retrofit

class MainViewModel:BaseViewModel<MainIntent, MainState>() {
    val service = retrofit.create(MainService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when(it) {
                    is MainIntent.Start -> start(it.count)
                }
            }
        }
    }
    fun start(count:Int) {
        viewModelScope.launch(Dispatchers.IO){
            for (i in count downTo 0) {
                state.value = MainState.Progress(i)
                Thread.sleep(1000)
            }
            state.value = MainState.Finish("结束")
        }

    }
}