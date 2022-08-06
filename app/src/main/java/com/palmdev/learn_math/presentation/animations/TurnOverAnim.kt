package com.palmdev.learn_math.presentation.animations

import android.view.View

object TurnOverAnim {
    fun anim(view: View, action: () -> Unit) {
        view.animate().scaleX(0f).scaleY(0.9f).setDuration(300).withEndAction {
            view.animate().scaleX(1f).scaleY(1f).duration = 300
            action.invoke()
        }
    }
}