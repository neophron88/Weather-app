@file:Suppress("unused")

package com.neophron88.library.ktx.fragment

/**
 * Copyright (C) 2022 neophron88
 */

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T> Fragment.viewLifeCycle(block: () -> T) = ViewLifeCycle(block)


class ViewLifeCycle<T>(
    private val block: () -> T
) : ReadOnlyProperty<Fragment, T> {

    private var value: T? = null

    private val error = IllegalStateException(
        "Don't access property before onViewCreated() or after onDestroyView() inclusive"
    )

    override operator fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T = value ?: tryToInit(thisRef)

    private val lifecycleObserver = object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            value = null
        }
    }

    private fun tryToInit(fragment: Fragment): T {
        fragment.view ?: throw error
        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) throw error
        lifecycle.addObserver(lifecycleObserver)

        val obj = block()
        value = obj
        return obj
    }
}