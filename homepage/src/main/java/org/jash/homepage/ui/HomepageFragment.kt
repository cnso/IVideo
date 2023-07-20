package org.jash.homepage.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jash.common.logDebug
import org.jash.homepage.adapter.SimpleTypeAdapter
import org.jash.homepage.dao.SimpleTypeDao
import org.jash.homepage.database.HomeDatabase
import org.jash.homepage.database.homeDatabase
import org.jash.homepage.databinding.FragmentHomepageBinding
import org.jash.homepage.viewmodel.HomepageIntent
import org.jash.homepage.viewmodel.HomepageState
import org.jash.homepage.viewmodel.HomepageViewModel
import org.jash.mvicore.BaseFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HomepageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomepageFragment : BaseFragment<FragmentHomepageBinding, HomepageViewModel>() {
    val adapter by lazy { SimpleTypeAdapter(childFragmentManager) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.intent.send(HomepageIntent.LoadType)
        }
        binding.pager.adapter = adapter
        binding.tab.setupWithViewPager(binding.pager)
        binding.pager.currentItem = 1
    }
    fun error(error:HomepageState.Error) {
        Toast.makeText(context, error.msg, Toast.LENGTH_LONG).show()
    }
    fun loaded(response: HomepageState.Response) {
        logDebug("网络类型 ${response.data}")
        if(adapter.types != response.data) {
            adapter.types.clear()
            adapter += response.data
        }
    }
    fun loadLocal(response: HomepageState.LocalResponse) {
        logDebug("本地类型 ${response.data}")
        adapter += response.data
    }

    override val defaultViewModelProviderFactory: ViewModelProvider.Factory
        get() = viewModelFactory { initializer { HomepageViewModel(requireContext().homeDatabase.getSimpleTypeDao()) } }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomepageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            HomepageFragment()
    }
}