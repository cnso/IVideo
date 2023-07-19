package org.jash.ivideo

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.jash.ivideo.databinding.ActivityMainBinding
import org.jash.ivideo.viewmodel.MainIntent
import org.jash.ivideo.viewmodel.MainState
import org.jash.ivideo.viewmodel.MainViewModel
import org.jash.mvicore.BaseActivity

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.tv.setOnClickListener {
//            lifecycleScope.launch {
//                viewModel.intent.send(MainIntent.GetSimpleVideo)
//            }
//        }
        lifecycleScope.launch {
            viewModel.intent.send(MainIntent.Start(10))
        }
        binding.version.text = "版本: ${BuildConfig.VERSION_NAME}"
    }
    fun loaded(response: MainState.Progress) {
        binding.timer.text = String.format("倒计时%2d", response.v)
    }
    fun error(finish: MainState.Finish) {
        Toast.makeText(this@MainActivity, finish.s, Toast.LENGTH_SHORT)
            .show()
    }
}