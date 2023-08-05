package org.jash.live

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.jash.common.adapter.CommonAdapter
import org.jash.common.logDebug
import org.jash.live.databinding.FragmentLiveRoomBinding
import org.jash.live.model.LiveRoom
import org.jash.live.viewmodel.LiveRoomIntent
import org.jash.live.viewmodel.LiveRoomState
import org.jash.live.viewmodel.LiveRoomViewModel
import org.jash.mvicore.BaseFragment


/**
 * A simple [Fragment] subclass.
 * Use the [LiveRoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LiveRoomFragment : BaseFragment<FragmentLiveRoomBinding, LiveRoomViewModel>() {
    val adapter by lazy { CommonAdapter<LiveRoom>(R.layout.item_room, BR.room) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(LiveRoomIntent.LoadRoom)
        }
        binding.recycler.adapter = adapter
    }
    fun loaded(response:LiveRoomState.RoomRespose) {
        adapter += response.data
    }
    fun error(error:LiveRoomState.Error) {
        Snackbar.make(binding.root, error.msg, Snackbar.LENGTH_LONG).show()
        logDebug(error)
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            LiveRoomFragment()
    }
}