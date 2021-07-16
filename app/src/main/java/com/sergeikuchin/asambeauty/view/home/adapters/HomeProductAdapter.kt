package com.sergeikuchin.asambeauty.view.home.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.R
import com.sergeikuchin.asambeauty.databinding.ItemProductHomeBinding
import com.sergeikuchin.asambeauty.domain.models.ItemPayload
import com.sergeikuchin.asambeauty.domain.models.ProductModel
import com.sergeikuchin.asambeauty.view.utils.*
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class HomeProductAdapter(
    private val viewEventDelegate: ViewEventDelegate
) : GeneralRecyclerViewAdapter<HomeProductViewHolder, ProductModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeProductViewHolder =
        ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let { binding ->
                val viewHolder = HomeProductViewHolder(binding)
                setOnClickListener(binding, viewHolder)
                viewHolder
            }

    override fun onBindViewHolder(holder: HomeProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onBindViewHolder(
        holder: HomeProductViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }

        var changed = false

        val payload = payloads.filterIsInstance<ItemPayload<*>>().last()
        val oldItem = payload.oldItemData as ProductModel
        val newItem = payload.newItemData as ProductModel

        if (oldItem.addedToCartList != newItem.addedToCartList) {
            holder.setAddedToCartList(newItem.addedToCartList)
            changed = true
        }

        if (oldItem.addedToWishList != newItem.addedToWishList) {
            holder.setAddedToWishList(newItem.addedToWishList)
            changed = true
        }

        if (!changed) {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    private fun setOnClickListener(
        binding: ItemProductHomeBinding,
        viewHolder: HomeProductViewHolder
    ) {
        binding.buyButton.setOnClickListener {
            viewEventDelegate.onViewEvent(ProductCartEventData(items[viewHolder.bindingAdapterPosition].id))
        }
        binding.wishlistButton.setOnClickListener {
            viewEventDelegate.onViewEvent(ProductWishEventData(items[viewHolder.bindingAdapterPosition].id))
        }
    }
}

class HomeProductViewHolder(
    private val binding: ItemProductHomeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ProductModel) {
        item.image.loadInto(binding.productImageView)
        binding.productTextView.text = item.title
        when (item.oldPrice != null) {
            true -> {
                binding.oldPriceTextView.visible()
                binding.oldPriceTextView.text = item.oldPrice.toString()
                binding.oldPriceTextView.paintFlags =
                    binding.oldPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                binding.priceTextView.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.pink_500
                    )
                )
            }
            false -> {
                binding.oldPriceTextView.invisible()
                binding.priceTextView.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.black
                    )
                )
            }
        }
        binding.priceTextView.text = item.price.toString()

        setAddedToCartList(item.addedToCartList)
        setAddedToWishList(item.addedToWishList)
    }

    fun setAddedToCartList(isAdded: Boolean) {
        when (isAdded) {
            true -> binding.buyButton.gone()
            false -> binding.buyButton.visible()
        }
    }

    fun setAddedToWishList(isAdded: Boolean) {
        when (isAdded) {
            true -> binding.wishlistButton.gone()
            false -> binding.wishlistButton.visible()
        }
    }
}

data class ProductCartEventData(
    val id: Long
) : ViewEventContext

data class ProductWishEventData(
    val id: Long
) : ViewEventContext