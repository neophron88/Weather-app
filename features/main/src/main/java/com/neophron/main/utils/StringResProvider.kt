package com.neophron.main.utils

import android.app.Application

interface StringResProvider {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg formatArgs: Any): String

}

class StringResProviderImpl(application: Application) : StringResProvider {

    private val context = application.applicationContext

    override fun getString(resId: Int): String =
        context.getString(resId)

    override fun getString(resId: Int, vararg formatArgs: Any): String =
        context.getString(resId, *formatArgs)

}

