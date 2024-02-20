package com.neophron88.library.ktx

import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updatePadding
import androidx.fragment.app.FragmentActivity

/**
* Copyright (C) 2022 neophron88
*/

sealed class InsetType {
    object SystemBars : InsetType()
    object StatusBar : InsetType()
    object NavigationBar : InsetType()
    object IME : InsetType()
}

sealed class UpdateParam {
    object Margin : UpdateParam()
    object Padding : UpdateParam()
}


fun InsetType.defineTarget(): Int =
    when (this) {
        is InsetType.SystemBars -> WindowInsetsCompat.Type.systemBars()
        is InsetType.StatusBar -> WindowInsetsCompat.Type.statusBars()
        is InsetType.NavigationBar -> WindowInsetsCompat.Type.navigationBars()
        is InsetType.IME -> WindowInsetsCompat.Type.ime()
    }


fun View.fitSystemUiOnce(
    insetType: InsetType,
    updateParam: UpdateParam = UpdateParam.Padding,
    appendPrevParam: Boolean = false
) {
    val insets = ViewCompat.getRootWindowInsets(this)
    if (insets != null) {
        fitSystemUiOnce(insets, insetType, updateParam, appendPrevParam)
    } else post {
        fitSystemUiOnce(insetType, updateParam, appendPrevParam)
    }
}


fun View.fitSystemUi(
    insetType: InsetType,
    updateParam: UpdateParam = UpdateParam.Padding,
    appendPrevParam: Boolean = false,
    onInsetChanged: () -> Unit = {}
) {

    ViewCompat.setOnApplyWindowInsetsListener(this) { v, wInsets ->
        v.fitSystemUiOnce(wInsets, insetType, updateParam, appendPrevParam)
        onInsetChanged()
        wInsets
    }
    requestApplyInsets()
}

private fun View.fitSystemUiOnce(
    wInsets: WindowInsetsCompat,
    insetType: InsetType,
    updateParam: UpdateParam,
    appendPrevParam: Boolean

) {

    val insets = wInsets.getInsets(insetType.defineTarget())

    val paramBottom: Int
    val paramTop: Int

    when (updateParam) {
        is UpdateParam.Padding -> {
            paramBottom = paddingBottom
            paramTop = paddingTop
        }

        is UpdateParam.Margin -> {
            paramBottom = marginBottom
            paramTop = marginTop
        }
    }

    when (insetType) {
        is InsetType.SystemBars -> {
            val bottom = if (appendPrevParam) insets.bottom + paramBottom else insets.bottom
            val top = if (appendPrevParam) insets.top + paramTop else insets.top
            when (updateParam) {
                is UpdateParam.Padding -> updatePadding(top = top, bottom = bottom)
                is UpdateParam.Margin -> updateLayoutParams<MarginLayoutParams> {
                    updateMargins(top = top, bottom = bottom)
                }
            }
        }

        is InsetType.StatusBar -> {
            val top = if (appendPrevParam) insets.top + paramTop else insets.top
            when (updateParam) {
                is UpdateParam.Padding -> updatePadding(top = top)
                is UpdateParam.Margin -> updateLayoutParams<MarginLayoutParams> {
                    updateMargins(top = top)
                }
            }
        }

        is InsetType.NavigationBar, InsetType.IME -> {
            val bottom = if (appendPrevParam) insets.bottom + paramBottom else insets.bottom
            when (updateParam) {
                is UpdateParam.Padding -> updatePadding(bottom = bottom)
                is UpdateParam.Margin -> updateLayoutParams<MarginLayoutParams> {
                    updateMargins(bottom = bottom)
                }
            }
        }
    }
}

fun View.getInsets(type: InsetType): Insets? {
    val wInsets = ViewCompat.getRootWindowInsets(this) ?: return null
    return wInsets.getInsets(type.defineTarget())
}


inline fun View.fitBottomSystemUi(
    crossinline onChanging: () -> Unit = {},
    crossinline onEnd: () -> Unit = {},
) {
    setWindowInsetsAnimationListener(
        onChanging = { insets ->
            val keyboardInset = insets.getInsets(WindowInsetsCompat.Type.ime())
            val navInset = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val bottom = kotlin.math.max(keyboardInset.bottom, navInset.bottom)
            updatePadding(bottom = bottom)
            onChanging()
        },
        onEnd = onEnd
    )
}

inline fun View.fitSystemIME(
    crossinline onChanging: () -> Unit = {},
    crossinline onEnd: () -> Unit = {},
) {
    setWindowInsetsAnimationListener(
        onChanging = { insets ->
            val keyboardInset = insets.getInsets(WindowInsetsCompat.Type.ime())
            val bottom = keyboardInset.bottom
            updatePadding(bottom = bottom)
            onChanging()
        },
        onEnd = onEnd
    )
}

inline fun View.setWindowInsetsAnimationListener(
    crossinline onChanging: (insets: WindowInsetsCompat) -> Unit,
    crossinline onEnd: () -> Unit = {}
) {
    val view = this
    ViewCompat.setWindowInsetsAnimationCallback(
        view,
        object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {
            override fun onProgress(
                insets: WindowInsetsCompat,
                runningAnimations: MutableList<WindowInsetsAnimationCompat>
            ): WindowInsetsCompat {
                onChanging(insets)
                return insets
            }

            override fun onEnd(animation: WindowInsetsAnimationCompat) = onEnd()
        }
    )
}

fun FragmentActivity.hideBars(insetType: InsetType) {

    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

    windowInsetsController.systemBarsBehavior =
        WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        window.attributes.layoutInDisplayCutoutMode =
            WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
    }

    windowInsetsController.hide(insetType.defineTarget())

}

fun FragmentActivity.showBars(insetType: InsetType) {
    val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
    windowInsetsController.show(insetType.defineTarget())
}

@Suppress("NOTHING_TO_INLINE")
inline fun FragmentActivity.notFitToSystemWindows() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

@Suppress("NOTHING_TO_INLINE")
inline fun FragmentActivity.fitToSystemWindows() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
}

@Suppress("NOTHING_TO_INLINE")
inline fun FragmentActivity.applyTransparentStatusBar() {
    window.statusBarColor = Color.TRANSPARENT
}

@Suppress("NOTHING_TO_INLINE")
inline fun FragmentActivity.applyTransparentNavigationBar() {
    window.navigationBarColor = Color.TRANSPARENT
    WindowCompat.getInsetsController(window, window.decorView).apply {
        isAppearanceLightNavigationBars = true
    }
}
