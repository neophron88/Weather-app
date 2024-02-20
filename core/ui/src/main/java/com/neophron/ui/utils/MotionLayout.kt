package com.neophron.ui.utils

import androidx.constraintlayout.motion.widget.MotionLayout

inline fun MotionLayout.onTransitionChange(
    crossinline action: (progress: Float) -> Unit
) = addTransitionListener(
    object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(
            motionLayout: MotionLayout?,
            startId: Int,
            endId: Int
        ) {
        }

        override fun onTransitionChange(
            motionLayout: MotionLayout?,
            startId: Int,
            endId: Int,
            progress: Float
        ) {
            action(progress)
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {}

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {
        }
    }
)
