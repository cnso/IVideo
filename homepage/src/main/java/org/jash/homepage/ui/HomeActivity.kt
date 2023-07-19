package org.jash.homepage.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import org.jash.homepage.R
import org.jash.homepage.databinding.ActivityHomeBinding
import org.jash.homepage.viewmodel.HomeViewModel
import org.jash.mvicore.BaseActivity

@Route(path = "/homemodel/home")
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, HomepageFragment.newInstance("", ""))
            .commit()
    }
}