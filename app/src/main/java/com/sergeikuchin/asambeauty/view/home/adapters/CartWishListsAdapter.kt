package com.sergeikuchin.asambeauty.view.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.databinding.ItemCartWishlistBinding
import com.sergeikuchin.asambeauty.domain.models.CartListModel
import com.sergeikuchin.asambeauty.domain.models.CartWishListModel
import com.sergeikuchin.asambeauty.domain.models.ItemPayload
import com.sergeikuchin.asambeauty.domain.models.WishListModel
import com.sergeikuchin.asambeauty.view.utils.ViewEventDelegate
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class CartWishListsAdapter(
    private val viewEventDelegate: ViewEventDelegate
) : GeneralRecyclerViewAdapter<CartWishListsViewHolder, CartWishListModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartWishListsViewHolder =
        CartWishListsViewHolder(
            binding = ItemCartWishlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            viewEventDelegate = viewEventDelegate
        )

    override fun onBindViewHolder(holder: CartWishListsViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(
        holder: CartWishListsViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        var changed = false

        val payload = payloads.filterIsInstance<ItemPayload<*>>().last()
        val oldItem = payload.oldItemData as CartWishListModel
        val newItem = payload.newItemData as CartWishListModel

        if (oldItem.cartListModel != newItem.cartListModel) {
            holder.setCartListAdapterItems(newItem.cartListModel)
            changed = true
        }

        if (oldItem.wishListModel != newItem.wishListModel) {
            holder.setWishListAdapterItems(newItem.wishListModel)
            changed = true
        }


        if (!changed) {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}

class CartWishListsViewHolder(
    private val binding: ItemCartWishlistBinding,
    viewEventDelegate: ViewEventDelegate
) : RecyclerView.ViewHolder(binding.root) {

    private val cartListAdapter: CartListAdapter = CartListAdapter(viewEventDelegate)
    private val wishListAdapter: WishListAdapter = WishListAdapter(viewEventDelegate)

    fun bind(item: CartWishListModel) {
        binding.root.layoutManager = LinearLayoutManager(binding.root.context)
            .apply { orientation = RecyclerView.HORIZONTAL }
        binding.root.adapter = ConcatAdapter(cartListAdapter, wishListAdapter)
        setCartListAdapterItems(item.cartListModel)
        setWishListAdapterItems(item.wishListModel)
    }

    fun setCartListAdapterItems(cartListModel: CartListModel?) {
        cartListAdapter.items = listOfNotNull(cartListModel)
    }

    fun setWishListAdapterItems(wishListModel: WishListModel?) {
        wishListAdapter.items = listOfNotNull(wishListModel)
    }
}

