package com.sergeikuchin.asambeauty.view.home.adapters

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.R
import com.sergeikuchin.asambeauty.databinding.ItemWishlistBinding
import com.sergeikuchin.asambeauty.domain.models.WishListModel
import com.sergeikuchin.asambeauty.view.utils.ViewEventContext
import com.sergeikuchin.asambeauty.view.utils.ViewEventDelegate
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class WishListAdapter(
    private val viewEventDelegate: ViewEventDelegate
) : GeneralRecyclerViewAdapter<WishListViewHolder, WishListModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishListViewHolder =
        ItemWishlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let { binding ->
                setOnClickListeners(binding)
                WishListViewHolder(binding, viewEventDelegate)
            }

    override fun onBindViewHolder(holder: WishListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun setOnClickListeners(binding: ItemWishlistBinding) {
        binding.seeAllButton.setOnClickListener {
            viewEventDelegate.onViewEvent(WishListEventData)
        }
    }
}

class WishListViewHolder(
    private val binding: ItemWishlistBinding,
    viewEventDelegate: ViewEventDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter: WishProductAdapter = WishProductAdapter(viewEventDelegate)

    fun bind(item: WishListModel) {
        binding.totalPriceTextView.text =
            binding.root.context.getString(
                R.string.total_price_title,
                "%.2f".format(item.totalPrice)
            )

        binding.wishRecyclerView.adapter = adapter
        adapter.items = item.wishItems

        binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
            val displayMetrics = DisplayMetrics()
            (binding.root.context as? Activity)?.let {
                it.windowManager.defaultDisplay.getMetrics(displayMetrics)
                width = displayMetrics.widthPixels / 100 * 90
            }
        }
    }
}

object WishListEventData : ViewEventContext