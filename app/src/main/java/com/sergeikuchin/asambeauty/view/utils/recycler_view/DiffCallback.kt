package com.sergeikuchin.asambeauty.view.utils.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.sergeikuchin.asambeauty.domain.models.AdapterModel

class DiffCallback(
    private val oldList: List<AdapterModel>,
    private val newList: List<AdapterModel>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].areItemsTheSame(newList[newItemPosition])

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean =
        oldList[oldPosition].areContentsTheSame(newList[newPosition])

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.getChangePayload(newItem)
    }
}