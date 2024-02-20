package com.neophron88.library.ktx

fun String.firstCharToUpperCase(): String {
    var newStr = this
    if (isNotBlank() && length > 1)
        newStr = this[0].uppercaseChar() + this.substring(1)

    return newStr
}