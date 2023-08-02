package org.jash.ivideo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.coroutines.launch
import org.jash.ivideo.databinding.ActivityMainBinding
import org.jash.ivideo.viewmodel.MainIntent
import org.jash.ivideo.viewmodel.MainState
import org.jash.ivideo.viewmodel.MainViewModel
import org.jash.mvicore.BaseActivity
import java.security.Permission

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(MainIntent.Start(2))
        }
        binding.version.text = "版本: ${BuildConfig.VERSION_NAME}"
        flag = permission.map { checkSelfPermission(it) }
            .map { it == PackageManager.PERMISSION_GRANTED }.reduce { a, b -> a && b }
        if (!flag) {
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                it.values.forEach { b ->
                    if (!b) {
                        finish()
                    }
                }
                jumpActivity()
            }.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
        }

    }
    fun progress(response: MainState.Progress) {
        binding.timer.text = String.format("倒计时%2d", response.v)
    }
    fun complete(finish: MainState.Finish) {
        if(flag) {
            jumpActivity()
        }
    }
    fun jumpActivity() {
        ARouter.getInstance()
            .build("/homemodel/home")
            .navigation()
        finish()
    }
}