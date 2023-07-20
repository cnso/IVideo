package org.jash.homepage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import org.jash.common.logDebug
import org.jash.homepage.R
import org.jash.homepage.database.homeDatabase
import org.jash.homepage.databinding.FragmentSimpleTypeBinding
import org.jash.homepage.viewmodel.HomepageState
import org.jash.homepage.viewmodel.HomepageViewModel
import org.jash.homepage.viewmodel.SimpleTypeIntent
import org.jash.homepage.viewmodel.SimpleTypeState
import org.jash.homepage.viewmodel.SimpleTypeViewModel
import org.jash.mvicore.BaseFragment

private const val CHANNEL_ID = "channelId"


/**
 * A simple [Fragment] subclass.
 * Use the [SimpleTypeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SimpleTypeFragment : BaseFragment<FragmentSimpleTypeBinding, SimpleTypeViewModel>() {
    // TODO: Rename and change types of parameters
    private lateinit var channelId: String
    private var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            channelId = it.getString(CHANNEL_ID, "")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(SimpleTypeIntent.GetLocalVideo(channelId))
            viewModel.intent.send(SimpleTypeIntent.GetRemoteVideo(channelId, page))
        }

    }
    fun error(error: SimpleTypeState.Error) {
        logDebug(error.msg)
        Toast.makeText(context, error.msg, Toast.LENGTH_LONG).show()
    }
    fun loaded(response: SimpleTypeState.RemoteResponse) {
        logDebug("网络类型 ${response.data}")

    }
    fun loadLocal(response: SimpleTypeState.LocalResponse) {
        logDebug("本地类型 ${response.data}")

    }
    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = viewModelFactory { initializer { SimpleTypeViewModel(requireContext().homeDatabase.getVideoDao()) } }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.

         * @return A new instance of fragment SimpleTypeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            SimpleTypeFragment().apply {
                arguments = Bundle().apply {
                    putString(CHANNEL_ID, param1)
                }
            }
    }
}