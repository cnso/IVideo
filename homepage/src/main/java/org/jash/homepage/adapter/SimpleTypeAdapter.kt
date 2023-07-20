package org.jash.homepage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import org.jash.homepage.model.SimpleType
import org.jash.homepage.ui.FlowFragment
import org.jash.homepage.ui.RecommendFragment
import org.jash.homepage.ui.SimpleTypeFragment

class SimpleTypeAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    val types:MutableList<SimpleType> = mutableListOf()
    override fun getCount(): Int {
        return types.size + 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> FlowFragment.newInstance()
            1 -> RecommendFragment.newInstance()
            else -> SimpleTypeFragment.newInstance(types[position - 2].channelid)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "关注"
            1 -> "推荐"
            else -> types[position - 2].typename
        }
    }
    operator fun plusAssign(list: List<SimpleType>) {
        types += list
        notifyDataSetChanged()
    }
}