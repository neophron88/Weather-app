package com.neophron.main.utils

import android.util.Log
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Fragment.showToast(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_LONG,
) {
    val view = requireView()
    val isShowed = (view.tag as? Boolean) ?: false
    if (isShowed) return
    view.tag = true
    Snackbar
        .make(view, message, duration)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
        .addCallback(
            object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    view.tag = false
                }
            }

        )
        .show()
}
