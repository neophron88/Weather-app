package com.neophron88.library.ktx.fragment

import android.graphics.drawable.GradientDrawable
import androidx.activity.OnBackPressedCallback
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
/**
 * Copyright (C) 2022 neophron88
 */


fun Fragment.getGradientDrawable(@DrawableRes res: Int): GradientDrawable {
    return ContextCompat.getDrawable(requireContext(), res) as GradientDrawable
}

fun Fragment.disableTransitionOverlap() {
    allowEnterTransitionOverlap = false
    allowReturnTransitionOverlap = false
}


inline fun <reified T> Fragment.findParentFragmentAs(): T {
    var target: T? = null

    var parent: Fragment? = parentFragment
    while (true) {
        if (parent == null) break
        else if (parent is T) {
            target = parent
            break
        }
        parent = parent.parentFragment

    }

    return target ?: error("No fragment parents, implemented ${T::class}")
}


fun Fragment.getStringOrNull(@StringRes resId: Int?): CharSequence? =
    if (resId == null) null else getString(resId)


fun Fragment.interceptOnBackPressed(
    lifecycleOwner: LifecycleOwner,
    callback: OnBackPressedCallback
) {
    requireActivity().onBackPressedDispatcher.addCallback(lifecycleOwner, callback)
}

inline fun onBackPressedCallback(
    enabled: Boolean = false,
    crossinline block: OnBackPressedCallback.() -> Unit
) = object : OnBackPressedCallback(enabled) {
    override fun handleOnBackPressed() {
        block()
    }
}