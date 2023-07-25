package org.jash.mine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.coroutines.launch
import org.jash.common.logDebug
import org.jash.mine.databinding.ActivityLoginBinding
import org.jash.mine.viewmodel.LoginIntent
import org.jash.mine.viewmodel.LoginState
import org.jash.mine.viewmodel.LoginViewModel
import org.jash.mvicore.BaseActivity
import org.jash.network.gson
import org.jash.network.user

@Route(path = "/mine/login")
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    val userMap by lazy { ObservableArrayMap<String, String>().apply {
        put("username", "")
        put("password", "")
    } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val preferences = getSharedPreferences("login", MODE_PRIVATE)
        val username = preferences.getString("username", "")
        userMap["username"] = username
        binding.user = userMap
        binding.login.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intent.send(LoginIntent.Login(userMap["username"] ?: "", userMap["password"] ?: ""))
            }
        }
        binding.register.setOnClickListener {
            ARouter.getInstance()
                .build("/mine/register")
                .navigation()
        }
        val intent = Intent(this, LoginActivity::class.java)
    }
    fun succeed(response: LoginState.Response) {
        user = response.user
        val preferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        preferences.edit()
            .putString("user", gson.toJson(user))
            .apply()
        logDebug(user)
        finish()
    }
    fun failure(error: LoginState.Error) {
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
        logDebug(error.msg)
    }
}