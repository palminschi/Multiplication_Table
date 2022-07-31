package com.palmdev.learn_math.presentation

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import com.palmdev.learn_math.R
import com.palmdev.learn_math.utils.MAIN

class MainActivity : AppCompatActivity() {

    // Funding Choice
    private var mConsentInformation: ConsentInformation? = null
    private var mConsentForm: ConsentForm? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MAIN = this
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navHostFragment.navController

        // Funding Choice
        val params = ConsentRequestParameters.Builder()
            .setTagForUnderAgeOfConsent(false)
            .build()
        mConsentInformation = UserMessagingPlatform.getConsentInformation(this)
        mConsentInformation?.requestConsentInfoUpdate(
            this,
            params,
            { // The consent information state was updated.
                // You are now ready to check if a form is available.
                if (mConsentInformation?.isConsentFormAvailable == true) {
                    loadForm()
                }
            },
            {
                // Handle the error.
            })
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        hideStatusBar()
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT < 30) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        } else if (Build.VERSION.SDK_INT >= 30) {
            window.decorView.windowInsetsController?.hide(
                android.view.WindowInsets.Type.statusBars()
            )
        }
    }

    // Funding Choice Form
    private fun loadForm() {
        UserMessagingPlatform.loadConsentForm(
            this,
            { consentForm ->
                this.mConsentForm = consentForm
                if (mConsentInformation!!.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                    consentForm.show(
                        this@MainActivity
                    ) { // Handle dismissal by reloading form.
                        loadForm()
                    }
                }
            }
        ) {
            // Handle the error
        }
    }
}