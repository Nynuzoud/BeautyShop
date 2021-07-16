package com.sergeikuchin.asambeauty.domain.models

interface AdapterModel {

    fun areItemsTheSame(other: AdapterModel): Boolean
    fun areContentsTheSame(other: AdapterModel): Boolean
    fun getChangePayload(other: AdapterModel): GenericItemPayload? = ItemPayload(
        oldItemData = this,
        newItemData = other
    )
}

interface GenericItemPayload

data class ItemPayload<T : AdapterModel>(
    val oldItemData: T,
    val newItemData: T
) : GenericItemPayload