package com.sergeikuchin.asambeauty.view.utils.recycler_view

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.domain.models.AdapterModel

abstract class GeneralRecyclerViewAdapter<VH : RecyclerView.ViewHolder, I : AdapterModel> : RecyclerView.Adapter<VH>() {

    open var items: List<I> = emptyList()
        set(value) {
            val diffCallback = DiffCallback(field, value)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun getItemCount(): Int = items.size
}