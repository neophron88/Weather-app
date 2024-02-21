package com.neophron.domain.results

import com.neophron.domain.errors.ErrorType


sealed class WeatherSingleResult<T> {
    class Success<T>(val value: T) : WeatherSingleResult<T>()
    class Error<T>(val type: ErrorType) : WeatherSingleResult<T>()
}


inline fun <T, R> WeatherSingleResult<T>.map(block: (T) -> R): WeatherSingleResult<R> =
    if (this is WeatherSingleResult.Success) WeatherSingleResult.Success(block(this.value))
    else this as WeatherSingleResult<R>

fun <T, R> WeatherSingleResult<T>.mapError(): WeatherSingleResult<R> =
    this as WeatherSingleResult<R>


sealed class WeatherResult<T> {
    class Success<T>(val value: T) : WeatherResult<T>()
    class Pending<T> : WeatherResult<T>()
    class Error<T>(val type: ErrorType) : WeatherResult<T>()
}


inline fun <T, R> WeatherResult<T>.map(block: (T) -> R): WeatherResult<R> =
    if (this is WeatherResult.Success) WeatherResult.Success(block(this.value))
    else this as WeatherResult<R>

fun <T> WeatherSingleResult<T>.isError() = this !is WeatherSingleResult.Success
fun <T> WeatherSingleResult<T>.isSuccess() = this is WeatherSingleResult.Success
fun <T> WeatherSingleResult<T>.requireValue(): T = (this as WeatherSingleResult.Success).value

fun <T> WeatherResult<T>.isError() = this !is WeatherResult.Success
fun <T> WeatherResult<T>.isSuccess() = this is WeatherResult.Success
fun <T> WeatherResult<T>.isPending() = this is WeatherResult.Pending
fun <T> WeatherResult<T>.requireValue(): T = (this as WeatherResult.Success).value

