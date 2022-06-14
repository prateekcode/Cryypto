package com.prateekcode.cryypto.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.ui.fragment.AllFragment
import com.prateekcode.cryypto.ui.fragment.TopGainerFragment
import com.prateekcode.cryypto.ui.fragment.TopLoosersFragment

class HomeFeedViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val titles = fragment.context?.resources?.getStringArray(R.array.home_feed_tabs)

    private val allFragment: AllFragment by lazy {
        AllFragment()
    }

    private val topGainerFragment: TopGainerFragment by lazy {
        TopGainerFragment()
    }

    private val topLooserFragment: TopLoosersFragment by lazy {
        TopLoosersFragment()
    }

    override fun getItemCount() = titles!!.size

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return allFragment
            1 -> return topGainerFragment
            2 -> return topLooserFragment
        }
        return allFragment
    }
}