package org.jash.homepage.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.jash.homepage.dao.VideoDao
import org.jash.homepage.model.BulletScreen
import org.jash.homepage.net.HomeService
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.network.retrofit

class DetailsViewModel(val videoDao: VideoDao):BaseViewModel<DetailsIntent, DetailsState>() {
    private val service = retrofit.create(HomeService::class.java)
    init {
        viewModelScope.launch {
            intent.consumeAsFlow().collect {
                when (it) {
                    is DetailsIntent.LoadLocal -> loadVideo(it.id)
                    is DetailsIntent.LoadBulletScreen -> loadBulletSrceen(it.itemid)
                    is DetailsIntent.SendBulletScreen -> sendBulletSrceen(it.bulletScreen)
                }
            }
        }
    }
    fun loadVideo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = DetailsState.LocalResponse(videoDao.getVideoById(id))
        }
    }
    fun loadBulletSrceen(itemId: String) {
        viewModelScope.launch {
            state.value = try {
                val res = service.getBulletScreenInfo(0, itemId)
                if (res.code == 0) {
                    DetailsState.BulletScreenResponse(res.data)
                } else {
                    DetailsState.Error(res.msg)

                }
            } catch (e:Exception) {
                DetailsState.Error(e.message ?: "")
            }
        }

    }
    fun sendBulletSrceen(bulletScreen: BulletScreen) {
        viewModelScope.launch {
            state.value = try {
                val res = service.sendBulletScreen(bulletScreen)
                if (res.code == 0) {
                    DetailsState.SendResponse(res.data)
                } else {
                    DetailsState.Error(res.msg)

                }
            } catch (e:Exception) {
                DetailsState.Error(e.message ?: "")
            }
        }

    }
}