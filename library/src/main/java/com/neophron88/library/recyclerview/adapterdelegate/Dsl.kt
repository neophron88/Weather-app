@file:Suppress("FunctionName")

package com.neophron88.library.recyclerview.adapterdelegate

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import kotlin.reflect.KClass

/**
 * Copyright (C) 2022 neophron88
 */


@DslMarker
annotation class AdapterDelegateDslMarker

@AdapterDelegateDslMarker
fun ItemsAdapter(block: ItemsAdapterBuilderDsl.() -> Unit): ItemsAdapter {
    val builderDsl = ItemsAdapterBuilderDsl()
    builderDsl.block()
    return builderDsl.build()
}

@AdapterDelegateDslMarker
class ItemsAdapterBuilderDsl {

    private val builder = MediatorItemDelegate.Builder()

    @Suppress("DEPRECATION")
    inline fun <reified I : Any, VB : ViewBinding> item(noinline block: ItemDelegateBuilderDsl<I, VB>.() -> Unit) {
        item(I::class, block)
    }

    @Deprecated("Use simple item { } function", ReplaceWith("item { }"), DeprecationLevel.WARNING)
    fun <I : Any, VB : ViewBinding> item(
        itemClass: KClass<I>,
        block: ItemDelegateBuilderDsl<I, VB>.() -> Unit
    ) {
        val itemDelegateBuilderDsl = ItemDelegateBuilderDsl<I, VB>()
        itemDelegateBuilderDsl.itemClass { itemClass }
        itemDelegateBuilderDsl.block()
        builder.addItemDelegate(itemDelegateBuilderDsl.build())
    }

    internal fun build() = ItemsAdapter(builder.build())

}

@AdapterDelegateDslMarker
class ItemDelegateBuilderDsl<I : Any, VB : ViewBinding> {

    private var itemClass: KClass<I>? = null
    private var layout: Int? = null
    private var diffUtil: DiffUtil.ItemCallback<I>? = null
    private var viewHolder: ((view: View) -> DslViewHolder<I, VB>)? = null


    internal fun itemClass(block: () -> KClass<I>) {
        this.itemClass = block()
    }

    fun layout(block: () -> Int) {
        this.layout = block()
    }

    fun diffUtil(block: DiffUtilBuilderDsl<I>.() -> Unit) {
        val builder = DiffUtilBuilderDsl<I>()
        builder.block()
        this.diffUtil = builder.build()
    }

    fun viewHolder(block: ViewHolderBuilderDsl<I, VB>.() -> Unit) {
        val builder = ViewHolderBuilderDsl<I, VB>()
        builder.block()
        this.viewHolder = { builder.build(it) }
    }

    internal fun build() = ItemDelegate(
        itemClass ?: throwFunctionNotInvoked("itemClass"),
        layout ?: throwFunctionNotInvoked("layout"),
        diffUtil ?: throwFunctionNotInvoked("diffutil"),
        viewHolder ?: throwFunctionNotInvoked("viewholder")
    )

}

@AdapterDelegateDslMarker
class DiffUtilBuilderDsl<I : Any> {

    private var onAreItemsTheSame: ((oldItem: I, newItem: I) -> Boolean)? = null
    private var onAreContentsTheSame: ((oldItem: I, newItem: I) -> Boolean)? = null
    private var onGetChangePayload: ((oldItem: I, newItem: I) -> Any?)? = null

    fun areItemsTheSame(block: (oldItem: I, newItem: I) -> Boolean) {
        onAreItemsTheSame = block
    }

    fun areContentsTheSame(block: (oldItem: I, newItem: I) -> Boolean) {
        onAreContentsTheSame = block
    }

    fun getChangePayload(block: (oldItem: I, newItem: I) -> Any) {
        onGetChangePayload = block
    }

    internal fun build() = ItemDiffUtil(
        onAreItemsTheSame ?: throwFunctionNotInvoked("areItemsTheSame"),
        onAreContentsTheSame ?: throwFunctionNotInvoked("areContentsTheSame"),
        onGetChangePayload
    )

}

@AdapterDelegateDslMarker
class ViewHolderBuilderDsl<I : Any, VB : ViewBinding> {
    private var binding: ((View) -> VB)? = null
    private var bindingCreated: (DslViewHolder<I, VB>.() -> Unit)? = null
    private var onBind: (DslViewHolder<I, VB>.() -> Unit)? = null
    private var onBindPayloads: (DslViewHolder<I, VB>.(payloads: MutableList<Any>) -> Unit)? = null
    private var unBind: (DslViewHolder<I, VB>.() -> Unit)? = null

    fun viewBinding(block: (View) -> VB) {
        this.binding = block
    }

    fun viewBindingCreated(block: DslViewHolder<I, VB>.() -> Unit) {
        this.bindingCreated = block
    }

    fun onBind(block: DslViewHolder<I, VB>.() -> Unit) {
        this.onBind = block
    }

    fun onBindPayloads(block: DslViewHolder<I, VB>.(payloads: MutableList<Any>) -> Unit) {
        this.onBindPayloads = block
    }

    fun unBind(block: DslViewHolder<I, VB>.() -> Unit) {
        this.unBind = block
    }


    internal fun build(view: View) = DslViewHolder(
        view,
        binding ?: throwFunctionNotInvoked("viewbinding"),
        bindingCreated,
        onBind ?: throwFunctionNotInvoked("onbind"),
        onBindPayloads,
        unBind
    )
}


class DslViewHolder<T : Any, VB : ViewBinding>(
    view: View,
    private val createBinding: (View) -> VB,
    private val onBindingCreated: (DslViewHolder<T, VB>.() -> Unit)?,
    private val onBindItem: DslViewHolder<T, VB>.() -> Unit,
    private val onBindItemPayloads: (DslViewHolder<T, VB>.(payloads: MutableList<Any>) -> Unit)?,
    private val unBindItem: (DslViewHolder<T, VB>.() -> Unit)?,
) : ItemViewHolder<T>(view) {

    val binding = createBinding(view)

    init {
        onBindingCreated?.invoke(this)
    }

    override fun onBind(item: T) {
        onBindItem()
    }

    override fun onBind(item: T, payloads: MutableList<Any>) {
        onBindItemPayloads?.invoke(this, payloads)
    }

    override fun unBind() {
        unBindItem?.invoke(this)
    }
}

private fun throwFunctionNotInvoked(name: String): Nothing =
    throw IllegalStateException("$name { } must be invoked")

fun <I : Any, VB : ViewBinding> ItemDelegateBuilderDsl<I, VB>.defaultDiffUtil(
    areItemsTheSame: (oldItem: I, newItem: I) -> Boolean = { oldItem, newItem -> oldItem == newItem }
) {
    diffUtil {
        areItemsTheSame(areItemsTheSame)
        areContentsTheSame { oldItem, newItem -> oldItem == newItem }
    }
}