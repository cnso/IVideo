package org.jash.homepage.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import org.jash.common.adapter.CommonAdapter
import org.jash.common.logDebug
import org.jash.homepage.R
import org.jash.homepage.BR
import org.jash.homepage.database.homeDatabase
import org.jash.homepage.viewmodel.CommentViewModel
import org.jash.mvicore.BaseFragment
import org.jash.homepage.databinding.FragmentCommentBinding
import org.jash.homepage.model.Comment
import org.jash.homepage.model.VideoModel
import org.jash.homepage.viewmodel.CommentIntent
import org.jash.homepage.viewmodel.CommentState
import org.jash.homepage.viewmodel.DetailsIntent
import org.jash.homepage.viewmodel.DetailsState
import org.jash.network.user
import java.lang.Error

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val VIDEO_ID = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [CommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentFragment : BaseFragment<FragmentCommentBinding, CommentViewModel>() {
    // TODO: Rename and change types of parameters
    private var id: Int? = null
    private val adapter = CommonAdapter<Comment>(R.layout.item_comment, BR.comment)
    private lateinit var video: VideoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getInt(VIDEO_ID)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(CommentIntent.LoadLocal(id ?: 0))
        }
        binding.recycler.adapter = adapter
        binding.send.setOnClickListener {
            logDebug("send $user")
            if (user == null) {
                Snackbar.make(binding.root, "请先登录", Snackbar.LENGTH_LONG).show()
//                Toast.makeText(requireActivity(), "请先登录", Toast.LENGTH_LONG).show()
            } else if (binding.content.text.isNullOrEmpty()) {
                Snackbar.make(binding.root, "评论为空", Snackbar.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    viewModel.intent.send(CommentIntent.PublishComment(Comment(datatype = 0, content = binding.content.text.toString(), itemid = video.item_id)))
                }
            }
        }
    }
    fun loaded(response: CommentState.LocalResponse) {
        video = response.data
        lifecycleScope.launch {
            viewModel.intent.send(CommentIntent.LoadComment(video.item_id))
        }
    }

    fun loadedComment(response: CommentState.CommentResponse) {
        adapter += response.data
    }
    fun published(response: CommentState.PublishResponse) {
        adapter += listOf(response.data)
        Snackbar.make(binding.root, "评论成功", Snackbar.LENGTH_LONG).show()
    }
    fun error(error: CommentState.Error) {
        Snackbar.make(binding.root, error.msg, Snackbar.LENGTH_LONG).show()
    }

    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { CommentViewModel(requireContext().homeDatabase.getVideoDao()) } }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param id Parameter 1.
         * @return A new instance of fragment CommentFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(id: Int) =
            CommentFragment().apply {
                arguments = Bundle().apply {
                    putInt(VIDEO_ID, id)
                }
            }
    }
}