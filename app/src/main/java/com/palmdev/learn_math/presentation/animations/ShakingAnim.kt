package com.palmdev.learn_math.presentation.animations

import android.view.View

object ShakingAnim {

    fun anim(view: View) {
        view.animate().translationX(8f).setDuration(20).withEndAction {
            view.animate().translationX(-16f).setDuration(20).withEndAction {
                view.animate().translationX(16f).setDuration(50).withEndAction {
                    view.animate().translationX(-8f).setDuration(50).withEndAction {
                        view.animate().translationX(0f).duration = 100
                    }
                }
            }
        }
    }

}