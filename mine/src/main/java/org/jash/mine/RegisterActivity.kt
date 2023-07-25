package org.jash.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.lifecycleScope
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.coroutines.launch
import org.jash.common.logDebug
import org.jash.mine.databinding.ActivityRegisterBinding
import org.jash.mine.viewmodel.LoginState
import org.jash.mine.viewmodel.RegisterIntent
import org.jash.mine.viewmodel.RegisterViewModel
import org.jash.mvicore.BaseActivity
import org.jash.network.user

@Route(path = "/mine/register")
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    val userMap = ObservableArrayMap<String, String>().apply {
        put("username", "")
        put("password", "")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.user = userMap
        binding.back.setOnClickListener {
            finish()
        }
        binding.register.setOnClickListener {
            lifecycleScope.launch {
                viewModel.intent.send(RegisterIntent.Register(userMap["username"] ?: "", userMap["password"] ?: ""))
            }
        }
    }
    fun succeed(response: LoginState.Response) {
        logDebug(response.user)
        finish()
    }
    fun failure(error: LoginState.Error) {
        Toast.makeText(this, error.msg, Toast.LENGTH_SHORT).show()
        logDebug(error.msg)
    }
}