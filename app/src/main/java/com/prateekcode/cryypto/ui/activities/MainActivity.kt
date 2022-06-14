package com.prateekcode.cryypto.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.databinding.ActivityMainBinding
import com.prateekcode.cryypto.utils.replaceFragmentInActivity
import com.prateekcode.cryypto.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val homeVm by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        replaceFragmentInActivity(homeVm.homeFragment, R.id.fcv_home_container)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_market -> {
                    replaceFragmentInActivity(
                        homeVm.homeFragment,
                        R.id.fcv_home_container
                    )
                    true
                }
                R.id.home_favorite -> {
                    replaceFragmentInActivity(
                        homeVm.favoriteFragment,
                        R.id.fcv_home_container
                    )
                    true
                }
                else -> {
                    replaceFragmentInActivity(
                        homeVm.homeFragment,
                        R.id.fcv_home_container
                    )
                    true
                }
            }
        }
    }


}