package com.palmdev.learn_math.presentation.animations

import android.view.View

object ExpansionReductionAnim {

    fun anim(view: View, infinitely: Boolean) {
        view.animate().scaleX(1.05f).scaleY(1.05f).setDuration(600).withEndAction {
            view.animate().scaleX(1f).scaleY(1f).setDuration(600).withEndAction {
                if (infinitely) anim(view, infinitely)
            }
        }
    }
}