package com.palmdev.learn_math.presentation.screens.select_table

import androidx.lifecycle.ViewModel
import com.palmdev.learn_math.utils.FirebaseEvents

class SelectTableViewModel : ViewModel() {
    init {
        FirebaseEvents().setScreenViewEvent(screenName = "Select Table")
    }
}