package com.prateekcode.cryypto.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.adapter.HomeFeedViewPagerAdapter
import com.prateekcode.cryypto.databinding.FragmentHomeBinding
import com.prateekcode.cryypto.utils.NetworkConnectionUtil
import com.prateekcode.cryypto.utils.hide
import com.prateekcode.cryypto.utils.show


class HomeFragment : Fragment() {

    private lateinit var mContext: Context
    private lateinit var homeFeedsViewPagerAdapter: HomeFeedViewPagerAdapter

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerInternet()
    }

    private fun initViewPager() {
        binding.apply {
            val tabTitles = context?.resources?.getStringArray(R.array.home_feed_tabs)
            homeFeedsViewPagerAdapter = HomeFeedViewPagerAdapter(this@HomeFragment)
            vpFragmentSlider.isSaveEnabled = false
            vpFragmentSlider.adapter = homeFeedsViewPagerAdapter
            val tabLayoutMediator = TabLayoutMediator(
                tabLayout,
                vpFragmentSlider
            ) { tabs: TabLayout.Tab, position: Int ->
                tabs.text = tabTitles!![position]
            }
            tabLayoutMediator.attach()
        }
    }

    private fun observerInternet() {
        NetworkConnectionUtil.getNetworkLiveData(mContext)
            .observe(viewLifecycleOwner) { isInternetAvailable ->
                if (isInternetAvailable) {
                    binding.apply {
                        tvNoInternetText.hide()
                        lottieNoInternet.hide()
                        vpFragmentSlider.show()
                    }
                    initViewPager()
                } else
                    binding.apply {
                        lottieNoInternet.show()
                        tvNoInternetText.show()
                        vpFragmentSlider.hide()
                    }
            }
    }
}