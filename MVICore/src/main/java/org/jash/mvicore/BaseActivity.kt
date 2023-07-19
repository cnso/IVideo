package org.jash.mvicore

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.mvicore.viewmodel.IState
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import kotlin.coroutines.Continuation

@Suppress("UNCHECKED_CAST")
open class BaseActivity<BINDING:ViewDataBinding, MODEL: BaseViewModel<*, *>>:AppCompatActivity() {
    lateinit var binding: BINDING
    lateinit var viewModel: MODEL
    private lateinit var map: Map<*, Method>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val type = this.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            // 所有泛型
            val types = type.actualTypeArguments
            val b = types[0]
            if (b is Class<*>) {
                val method = b.getMethod("inflate", LayoutInflater::class.java)
                binding = method.invoke(null, layoutInflater) as BINDING
                setContentView(binding.root)
            }
            val vm = types[1]
            if (vm is Class<*>) {
                viewModel = ViewModelProvider(this).get(vm as Class<ViewModel>) as MODEL
                val vmSuper = vm.genericSuperclass
                if (vmSuper is ParameterizedType) {
                    val state = vmSuper.actualTypeArguments[1]
                    if (state is Class<*>) {
                        map = this.javaClass.methods
                            .filter { it.parameterTypes.size == 1 && state.isAssignableFrom(it.parameterTypes[0]) }
                            .associateBy { it.parameterTypes[0] }
                        lifecycleScope.launch {
                            viewModel.state.collect(this@BaseActivity::collect)
                        }

                    }
                }
            }
        }
    }
    private fun collect(state: IState?) {
        map[state?.javaClass]?.invoke(this, state)
    }
}
