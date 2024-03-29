package com.neophron88.library.singleusedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

abstract class SingleUseData<T : Any> {

    protected abstract val liveData: LiveData<Handler<T>>

    abstract fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<T>)

}