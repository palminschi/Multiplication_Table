package com.palmdev.learn_math.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.palmdev.learn_math.MAIN
import com.palmdev.learn_math.R

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MAIN = this

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        hideStatusBar()
    }

    private fun hideStatusBar(){
        if (Build.VERSION.SDK_INT < 30) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else if (Build.VERSION.SDK_INT >= 30) {
            window.decorView.windowInsetsController?.hide(
                android.view.WindowInsets.Type.statusBars()
            )
        }
    }

}