package com.neophron88.library.singleusedata

class Handler<T>(private var value: T?) {

    fun getVal(): T? {
        return value?.also { value = null }
    }
}