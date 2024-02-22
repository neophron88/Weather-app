package com.neophron.data.base

import com.neophron.domain.errors.ErrorType
import com.neophron.network.base.HttpCode.BACKEND
import com.neophron.network.base.HttpCode.CLIENT
import retrofit2.HttpException
import java.io.IOException


internal inline fun runCatch(
    run: () -> Unit,
    failed: (e: Exception) -> Unit
) = try {
    run()
} catch (e: Exception) {
    failed(e)
}

internal fun Exception.toErrorType(): ErrorType = when (this) {
    is HttpException -> {
        val code = this.code()
        if (code >= BACKEND) ErrorType.BackendFailed
        else if (code in CLIENT until BACKEND) {
            when (code) {
                404 -> ErrorType.NoSuchCity
                401 -> ErrorType.AuthorizationFailed
                else -> ErrorType.Unknown(this)
            }
        } else ErrorType.Unknown(this)
    }

    is IOException -> ErrorType.NoConnection
    else -> throw this
}

