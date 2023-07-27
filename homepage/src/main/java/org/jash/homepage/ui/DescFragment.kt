package org.jash.homepage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.launch
import org.jash.common.logDebug
import org.jash.homepage.R
import org.jash.homepage.database.homeDatabase
import org.jash.homepage.databinding.FragmentDescBinding
import org.jash.homepage.viewmodel.DetailsIntent
import org.jash.homepage.viewmodel.DetailsState
import org.jash.homepage.viewmodel.DetailsViewModel
import org.jash.mvicore.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val VIDEO_ID = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [DescFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescFragment : BaseFragment<FragmentDescBinding, DetailsViewModel>() {
    // TODO: Rename and change types of parameters
    private var id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(VIDEO_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(DetailsIntent.LoadLocal(id ?: 0))
        }
    }

    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { DetailsViewModel(requireContext().homeDatabase.getVideoDao()) } }

    fun loaded(response: DetailsState.LocalResponse) {
        binding.video = response.data
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param id Parameter 1.
         * @return A new instance of fragment DescFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int) =
            DescFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIDEO_ID, id)
                }
            }
    }
}