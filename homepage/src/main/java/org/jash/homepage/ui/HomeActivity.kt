package org.jash.homepage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import org.jash.common.logDebug
import org.jash.homepage.R
import org.jash.homepage.databinding.ActivityHomeBinding
import org.jash.homepage.viewmodel.HomeViewModel
import org.jash.mine.MineFragment
import org.jash.mvicore.BaseActivity
import org.jash.network.user

@Route(path = "/homemodel/home")
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, HomepageFragment.newInstance())
            .commit()
        binding.navigation.setOnItemSelectedListener {
            if(it.itemId == R.id.home_mine && user == null) {
                ARouter.getInstance()
                    .build("/mine/login")
                    .navigation()
                return@setOnItemSelectedListener false
            }
            val fragment = supportFragmentManager.fragments[0]
            if (it.itemId != when(fragment) {
                is HomepageFragment -> R.id.home_homepage
                is MineFragment -> R.id.home_mine
                else -> R.id.home_homepage
            }) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content,
                        when(it.itemId) {
                            R.id.home_homepage -> HomepageFragment.newInstance()
                            R.id.home_mine -> MineFragment.newInstance()
                            else -> HomepageFragment.newInstance()
                        }
                    )
                    .commit()
            }
//            if ((it.itemId == R.id.home_homepage && fragment !is HomepageFragment) ||
//                (it.itemId == R.id.home_mine && fragment !is MineFragment)){
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.content,
//                        when(it.itemId) {
//                            R.id.home_homepage -> HomepageFragment.newInstance()
//                            R.id.home_mine -> MineFragment.newInstance()
//                            else -> HomepageFragment.newInstance()
//                        }
//                    )
//                    .commit()
//            }

            true
        }
        supportFragmentManager.addFragmentOnAttachListener { _, fragment ->
            val itemId = when(fragment) {
                is HomepageFragment -> R.id.home_homepage
                is MineFragment -> R.id.home_mine
                else -> R.id.home_homepage
            }
            if(itemId != binding.navigation.selectedItemId) {
                binding.navigation.selectedItemId = itemId
            }
        }
    }

}