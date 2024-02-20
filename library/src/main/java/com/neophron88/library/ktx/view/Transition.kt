package com.neophron88.library.ktx.view

import androidx.transition.Transition

inline fun Transition.addListener(
    crossinline onStart: (Transition) -> Unit = {},
    crossinline onEnd: (Transition) -> Unit = {},
    crossinline onCancel: (Transition) -> Unit = {},
    crossinline onPause: (Transition) -> Unit = {},
    crossinline onResume: (Transition) -> Unit = {},
) = addListener(
    object : Transition.TransitionListener {
        override fun onTransitionStart(transition: Transition) = onStart(transition)

        override fun onTransitionEnd(transition: Transition) = onEnd(transition)

        override fun onTransitionCancel(transition: Transition) = onCancel(transition)

        override fun onTransitionPause(transition: Transition) = onPause(transition)

        override fun onTransitionResume(transition: Transition) = onResume(transition)

    }
)
