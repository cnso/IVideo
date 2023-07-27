package org.jash.homepage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.coroutines.launch
import org.jash.common.lifecycle.GSYLifecycleObserver
import org.jash.common.logDebug
import org.jash.homepage.R
import org.jash.homepage.adapter.DetailsAdapter
import org.jash.homepage.database.homeDatabase
import org.jash.homepage.databinding.ActivityDetailsBinding
import org.jash.homepage.model.VideoModel
import org.jash.homepage.viewmodel.DetailsIntent
import org.jash.homepage.viewmodel.DetailsState
import org.jash.homepage.viewmodel.DetailsViewModel
import org.jash.mvicore.BaseActivity

@Route(path = "/homemodel/details")
class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {
    @Autowired(name = "id")
    @JvmField
    var id:Int = 0
    lateinit var video:VideoModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        initView()
        lifecycleScope.launch {
            viewModel.intent.send(DetailsIntent.LoadLocal(id))
        }
    }
    private fun initView() {
        binding.pager.adapter = DetailsAdapter(supportFragmentManager, id)
        binding.tab.setupWithViewPager(binding.pager)
        lifecycle.addObserver(GSYLifecycleObserver(binding.video))
        binding.video.backButton.setOnClickListener { finish() }
    }
    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { DetailsViewModel(homeDatabase.getVideoDao()) } }

    fun loaded(response: DetailsState.LocalResponse) {
        video = response.data
        logDebug(video)
        binding.video.setUp(video.videopath, true, video.title)
    }
}