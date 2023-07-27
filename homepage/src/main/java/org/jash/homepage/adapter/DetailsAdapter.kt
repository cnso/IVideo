package org.jash.homepage.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import org.jash.homepage.model.SimpleType
import org.jash.homepage.model.VideoModel
import org.jash.homepage.ui.CommentFragment
import org.jash.homepage.ui.DescFragment
import org.jash.homepage.ui.FlowFragment
import org.jash.homepage.ui.RecommendFragment
import org.jash.homepage.ui.SimpleTypeFragment

class DetailsAdapter(fragmentManager: FragmentManager, val id:Int):FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> DescFragment.newInstance(id)
            1 -> CommentFragment.newInstance(id)
            else -> DescFragment.newInstance(id)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "简介"
            1 -> "评论"
            else -> "简介"
        }
    }
}