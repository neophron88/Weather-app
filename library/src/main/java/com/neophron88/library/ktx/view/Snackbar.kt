package com.neophron88.library.ktx.view

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun View.showToast(@StringRes message: Int) {
    Snackbar
        .make(this, message, Snackbar.LENGTH_LONG)
        .setAnchorView(this)
        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
        .show()
}