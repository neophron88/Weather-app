package com.neophron.domain.errors


sealed class ErrorType  {
    object NoConnection : ErrorType()
    object BackendFailed : ErrorType()
    object AuthorizationFailed : ErrorType()
    object NoSuchCity : ErrorType()
    object EmptyList : ErrorType()
    class Unknown(val e: Exception? = null) : ErrorType()
}