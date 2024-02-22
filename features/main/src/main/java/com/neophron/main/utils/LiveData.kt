package com.neophron.main.utils

import androidx.lifecycle.MutableLiveData
import com.neophron88.library.ktx.require
import com.neophron88.library.singleusedata.MutableSingleUseData

inline fun <T> MutableLiveData<T>.updateValue(
    block: (T) -> T
) {
    value = block(this.value.require())
}

inline fun <T : Any> MutableSingleUseData<T>.updateValue(
    block: (T?) -> T
) {
    value = block(this.value)
}
