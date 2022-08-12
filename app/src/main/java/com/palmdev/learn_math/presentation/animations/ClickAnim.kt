package com.palmdev.learn_math.presentation.animations

import android.view.View

object ClickAnim {
    fun anim(view: View) {
        view.animate().scaleX(1.15f).scaleY(1.15f).setDuration(15).withEndAction {
            view.animate().scaleX(1f).scaleY(1f).duration = 50
        }
    }
}