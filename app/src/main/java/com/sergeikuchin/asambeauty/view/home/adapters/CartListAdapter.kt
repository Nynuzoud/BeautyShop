package com.sergeikuchin.asambeauty.view.home.adapters

import android.app.Activity
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.R
import com.sergeikuchin.asambeauty.databinding.ItemCartBinding
import com.sergeikuchin.asambeauty.domain.models.CartListItemModel
import com.sergeikuchin.asambeauty.domain.models.CartListModel
import com.sergeikuchin.asambeauty.view.utils.ViewEventContext
import com.sergeikuchin.asambeauty.view.utils.ViewEventDelegate
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class CartListAdapter(
    private val viewEventDelegate: ViewEventDelegate
) : GeneralRecyclerViewAdapter<CartListViewHolder, CartListModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder =
        ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let { binding ->
                setOnClickListeners(binding)
                CartListViewHolder(binding, viewEventDelegate)
            }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun setOnClickListeners(binding: ItemCartBinding) {
        binding.seeAllButton.setOnClickListener {
            viewEventDelegate.onViewEvent(CartListEventData)
        }
    }
}

class CartListViewHolder(
    private val binding: ItemCartBinding,
    viewEventDelegate: ViewEventDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private val adapter: CartProductAdapter = CartProductAdapter(viewEventDelegate)

    fun bind(item: CartListModel) {
        binding.cartRecyclerView.adapter = adapter

        setTotalPriceText(item.totalPrice)
        setAdapterItems(item.cartItems)

        binding.root.updateLayoutParams<ViewGroup.LayoutParams> {
            val displayMetrics = DisplayMetrics()
            (binding.root.context as? Activity)?.let {
                it.windowManager.defaultDisplay.getMetrics(displayMetrics)
                width = displayMetrics.widthPixels / 100 * 90
            }
        }
    }

    fun setTotalPriceText(totalPrice: Float) {
        binding.totalPriceTextView.text =
            binding.root.context.getString(
                R.string.total_price_title,
                "%.2f".format(totalPrice)
            )
    }

    fun setAdapterItems(cartItems: List<CartListItemModel>) {
        adapter.items = cartItems
    }
}

object CartListEventData : ViewEventContext