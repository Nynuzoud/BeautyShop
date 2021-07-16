package com.sergeikuchin.asambeauty.view.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.R
import com.sergeikuchin.asambeauty.databinding.ItemWishlistProductBinding
import com.sergeikuchin.asambeauty.domain.models.WishListItemModel
import com.sergeikuchin.asambeauty.view.utils.ViewEventContext
import com.sergeikuchin.asambeauty.view.utils.ViewEventDelegate
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class WishProductAdapter(
    private val viewEventDelegate: ViewEventDelegate
) : GeneralRecyclerViewAdapter<WishProductViewHolder, WishListItemModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WishProductViewHolder =
        ItemWishlistProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let { binding ->
                val viewHolder = WishProductViewHolder(binding)
                setOnClickListeners(binding, viewHolder)
                viewHolder
            }

    override fun onBindViewHolder(holder: WishProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun setOnClickListeners(
        binding: ItemWishlistProductBinding,
        holder: WishProductViewHolder
    ) {
        binding.quantityButton.setOnClickListener {
            showDropDownMenu(
                view = it,
                item = items[holder.bindingAdapterPosition]
            )
        }
        binding.productAddToCartButton.setOnClickListener {
            viewEventDelegate.onViewEvent(
                viewEventContext = WishProductAddToCartEventData(items[holder.bindingAdapterPosition].id)
            )
        }
    }

    private fun showDropDownMenu(view: View, item: WishListItemModel) {
        PopupMenu(view.context, view).apply {
            menuInflater.inflate(R.menu.menu_item_product_quantity, menu)
            setOnMenuItemClickListener {
                viewEventDelegate.onViewEvent(WishProductQuantityEventData(item.id, it.title.toString().toInt()))
                true
            }
            show()
        }
    }
}

class WishProductViewHolder(
    private val binding: ItemWishlistProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: WishListItemModel) {
        item.image.loadInto(binding.productImageView)
        binding.productTitleTextView.text = item.title
        binding.productPriceTextView.text = item.price.toString()
        binding.quantityButton.text = item.count.toString()
        when (item.oldPrice != null) {
            true -> binding.productPriceTextView.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.pink_500
                )
            )
            false -> binding.productPriceTextView.setTextColor(
                ContextCompat.getColor(
                    binding.root.context,
                    R.color.black
                )
            )
        }
    }
}

data class WishProductQuantityEventData(
    val id: Long,
    val count: Int
) : ViewEventContext

data class WishProductAddToCartEventData(
    val id: Long
) : ViewEventContext