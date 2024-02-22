@file:Suppress("unused")

package com.neophron88.library.ktx.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
/**
 * Copyright (C) 2022 neophron88
 */

/**
 * A delegate that provides your viewbinding within a Fragment by lazy.
 *
 * The Delegate considers the fragment's view lifecycle.
 *
 * When using this delegate make sure the Fragment is inherited from
 * Fragment(@LayoutRes int contentLayoutId) with providing your layout in constructor or
 * create as usual in onCreateView method in order to viewBinding's delegate will be able
 * to bind to the view on its own.
 *
 * Note that accessing viewBinding while fragment's view is
 * destroyed or not created will throw IllegalStateException.
 **/

inline fun <reified VB : ViewBinding> Fragment.viewBindings(): ViewLifeCycle<VB> =
    viewLifeCycle {
        val bindMethod = VB::class.java.getMethod("bind", View::class.java)
        bindMethod.invoke(null, view) as VB
    }
