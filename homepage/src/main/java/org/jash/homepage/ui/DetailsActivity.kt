package org.jash.homepage.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.snackbar.Snackbar
import com.shuyu.gsyvideoplayer.listener.GSYMediaPlayerListener
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.coroutines.launch
import master.flame.danmaku.danmaku.model.BaseDanmaku
import master.flame.danmaku.danmaku.model.Duration
import master.flame.danmaku.danmaku.model.IDanmakus
import master.flame.danmaku.danmaku.model.IDisplayer
import master.flame.danmaku.danmaku.model.R2LDanmaku
import master.flame.danmaku.danmaku.model.android.DanmakuContext
import master.flame.danmaku.danmaku.model.android.Danmakus
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser
import org.jash.common.lifecycle.GSYLifecycleObserver
import org.jash.common.logDebug
import org.jash.homepage.R
import org.jash.homepage.adapter.DetailsAdapter
import org.jash.homepage.database.homeDatabase
import org.jash.homepage.databinding.ActivityDetailsBinding
import org.jash.homepage.model.BulletScreen
import org.jash.homepage.model.Comment
import org.jash.homepage.model.VideoModel
import org.jash.homepage.viewmodel.CommentIntent
import org.jash.homepage.viewmodel.DetailsIntent
import org.jash.homepage.viewmodel.DetailsState
import org.jash.homepage.viewmodel.DetailsViewModel
import org.jash.mvicore.BaseActivity
import org.jash.network.user

@Route(path = "/homemodel/details")
class DetailsActivity : BaseActivity<ActivityDetailsBinding, DetailsViewModel>() {
    @Autowired(name = "id")
    @JvmField
    var id:Int = 0
    lateinit var video:VideoModel
    lateinit var danmakuContext:DanmakuContext
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
        binding.video.setVideoAllCallBack(object : GSYSampleCallBack() {
            override fun onPrepared(url: String?, vararg objects: Any?) {
                binding.danmaku.start()
            }
        })
        binding.danmaku.enableDanmakuDrawingCache(true)
        //DanmakuContext主要用于弹幕样式的设置
        danmakuContext = DanmakuContext.create()
        danmakuContext.setDanmakuStyle(IDisplayer.DANMAKU_STYLE_STROKEN, 3F) //描边
        danmakuContext.setDuplicateMergingEnabled(true) //重复合并
        danmakuContext.setScrollSpeedFactor(1.2f) //弹幕滚动速度
        //让弹幕进入准备状态，传入弹幕解析器和样式设置
        binding.danmaku.prepare(object :BaseDanmakuParser() {
            override fun parse(): IDanmakus = Danmakus()
        }, danmakuContext)
        binding.send.setOnClickListener {
            if (user == null) {
                Snackbar.make(binding.root, "请先登录", Snackbar.LENGTH_LONG).show()
//                Toast.makeText(requireActivity(), "请先登录", Toast.LENGTH_LONG).show()
            } else if (binding.bullet.text.isNullOrEmpty()) {
                Snackbar.make(binding.root, "弹幕为空", Snackbar.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    viewModel.intent.send( DetailsIntent.SendBulletScreen(BulletScreen(content = binding.bullet.text.toString(), itemid = video.item_id, datatype = 0)))
                }
            }
        }

    }
    override val defaultViewModelProviderFactory
        get() = viewModelFactory { initializer { DetailsViewModel(homeDatabase.getVideoDao()) } }

    fun loaded(response: DetailsState.LocalResponse) {
        video = response.data
        binding.video.setUp(video.videopath, true, video.title)
        lifecycleScope.launch {
            viewModel.intent.send(DetailsIntent.LoadBulletScreen(video.item_id))
        }

    }
    fun loadedBulletScreen(response: DetailsState.BulletScreenResponse) {
        response.data.map {
            danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL).apply {
                this.text = it.content
                this.textColor = Color.WHITE
                this.borderColor = Color.BLACK
                this.textSize = 100f
            }
        }.forEach(binding.danmaku::addDanmaku)
        Snackbar.make(binding.root, "弹幕装载完毕", Snackbar.LENGTH_LONG).show()
    }
    fun sendSucceed(response: DetailsState.SendResponse) {
        danmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL).apply {
            this.text = response.data.content
            this.textColor = Color.WHITE
            this.textSize = 100f
            binding.danmaku.addDanmaku(this)
        }
        Snackbar.make(binding.root, "发布弹幕成功", Snackbar.LENGTH_LONG).show()

    }
    fun error(error:DetailsState.Error) {
        Snackbar.make(binding.root, error.msg, Snackbar.LENGTH_LONG).show()
    }
}