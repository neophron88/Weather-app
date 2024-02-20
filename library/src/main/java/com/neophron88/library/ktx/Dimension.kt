package com.neophron88.library.ktx

import android.content.Context
import android.util.TypedValue
import androidx.annotation.DimenRes

@MustBeDocumented
@Retention(AnnotationRetention.SOURCE)
@Target(
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.FIELD,
    AnnotationTarget.LOCAL_VARIABLE
)
annotation class Dp

fun Int.dp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    context.resources.displayMetrics
).toInt()

fun Float.dp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
)

fun Int.sp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_SP,
    this.toFloat(),
    context.resources.displayMetrics
)

fun Context.getDimen(@DimenRes id: Int) =
    resources.getDimension(id).toInt()

