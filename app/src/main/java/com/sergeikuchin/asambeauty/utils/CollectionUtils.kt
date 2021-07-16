package com.sergeikuchin.asambeauty.utils

fun <T> MutableList<T>.replace(oldItem: T, newItem: T): MutableList<T> =
    run {
        val oldItemIndex = indexOf(oldItem)
        removeAt(oldItemIndex)
        add(oldItemIndex, newItem)
        this
    }