@file:Suppress("FunctionName")

package com.neophron.ui.utils

import android.content.Context
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neophron88.library.ktx.getDimen
import java.lang.Integer.max

fun HorizontalLinearLayoutManager(
    context: Context
) = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

fun VerticalLinearLayoutManager(
    context: Context
) = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

fun RecyclerView.setAutoSpanGridLayoutManager(
    context: Context,
    @DimenRes approximateItemWidth: Int
) = post {
    val manager = GridLayoutManager(context, 1)
    layoutManager = manager
    val widthView = width - (paddingLeft + paddingRight)
    val dimen = context.getDimen(approximateItemWidth)
    val spanCount = (widthView / dimen)
    manager.spanCount = max(spanCount, 2)
    manager.requestLayout()
}

