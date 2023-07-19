package org.jash.mvicore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.jash.mvicore.viewmodel.BaseViewModel
import org.jash.mvicore.viewmodel.IState
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

open class BaseFragment<BINDING: ViewDataBinding, MODEL: BaseViewModel<*, *>>:Fragment() {
    lateinit var binding: BINDING
    lateinit var viewModel: MODEL
    private lateinit var map: Map<*, Method>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = this.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            // 所有泛型
            val types = type.actualTypeArguments
            val b = types[0]
            if (b is Class<*>) {
                val method = b.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
                binding = method.invoke(null, inflater, container, false) as BINDING
            }
            val vm = types[1]
            if (vm is Class<*>) {
                viewModel = ViewModelProvider(requireActivity()).get(vm as Class<ViewModel>) as MODEL
                val vmSuper = vm.genericSuperclass
                if (vmSuper is ParameterizedType) {
                    val state = vmSuper.actualTypeArguments[1]
                    if (state is Class<*>) {
                        map = this.javaClass.methods
                            .filter { it.parameterTypes.size == 1 && state.isAssignableFrom(it.parameterTypes[0]) }
                            .associateBy { it.parameterTypes[0] }
                        lifecycleScope.launch {
                            viewModel.state.collect(this@BaseFragment::collect)
                        }

                    }
                }
            }
        }
        return binding.root
    }
    private fun collect(state: IState?) {
        map[state?.javaClass]?.invoke(this, state)
    }
}