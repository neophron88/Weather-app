package com.neophron88.library.ktx

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.os.HandlerCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

inline fun LifecycleOwner.onDestroy(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            block()
        }
    })
}


inline fun LifecycleOwner.onPause(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onPause(owner: LifecycleOwner) {
            block()
        }
    })
}

inline fun LifecycleOwner.onStop(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStop(owner: LifecycleOwner) {
            block()
        }
    })
}

inline fun LifecycleOwner.onStart(crossinline block: () -> Unit) {
    this.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onStart(owner: LifecycleOwner) {
            block()
        }
    })
}

fun LifecycleOwner.post(
    delayMillis: Long = 0,
    handler: Handler = Handler(Looper.getMainLooper()),
    removeWhenAtLeast: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    token: Any? = null,
    run: () -> Unit
): Handler {

    if (this.lifecycle.currentState == Lifecycle.State.DESTROYED) return handler

    val observer = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event >= removeWhenAtLeast) {
                handler.removeCallbacksAndMessages(token)
                this@post.lifecycle.removeObserver(this)
            }
        }
    }

    val runnable = Runnable {
        if (this.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            run.invoke()
            this.lifecycle.removeObserver(observer)
        }
    }

    this.lifecycle.addObserver(observer)
    HandlerCompat.postDelayed(handler, runnable, token, delayMillis)
    return handler
}