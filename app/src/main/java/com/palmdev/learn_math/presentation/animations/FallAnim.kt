package com.palmdev.learn_math.presentation.animations

import android.view.View
import android.view.animation.AccelerateInterpolator

object FallAnim {
    fun anim(view: View, durationInMillis: Long) {
        val bottomOfScreen: Float = view.context.resources.displayMetrics.heightPixels.toFloat()

        view.animate().translationY(0f).setDuration(0).withEndAction {
            view.animate().translationY(bottomOfScreen).setInterpolator(AccelerateInterpolator()).duration = durationInMillis
        }.start()
    }
}