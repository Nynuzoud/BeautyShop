package com.sergeikuchin.asambeauty.view.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sergeikuchin.asambeauty.R
import com.sergeikuchin.asambeauty.databinding.ItemCartProductBinding
import com.sergeikuchin.asambeauty.domain.models.CartListItemModel
import com.sergeikuchin.asambeauty.view.utils.ViewEventContext
import com.sergeikuchin.asambeauty.view.utils.ViewEventDelegate
import com.sergeikuchin.asambeauty.view.utils.recycler_view.GeneralRecyclerViewAdapter

class CartProductAdapter(
    private val viewEventDelegate: ViewEventDelegate
) : GeneralRecyclerViewAdapter<CartProductViewHolder, CartListItemModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder =
        ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .let { binding ->
                val viewHolder = CartProductViewHolder(binding)
                setOnClickListeners(binding, viewHolder)
                viewHolder
            }

    override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
        holder.bind(items[position])
    }

    private fun setOnClickListeners(
        binding: ItemCartProductBinding,
        holder: CartProductViewHolder
    ) {
        binding.quantityButton.setOnClickListener {
            showDropDownMenu(
                view = it,
                item = items[holder.bindingAdapterPosition]
            )
        }
        binding.productRemoveButton.setOnClickListener {
            viewEventDelegate.onViewEvent(
                viewEventContext = CartProductRemoveEventData(items[holder.bindingAdapterPosition].id)
            )
        }
    }

    private fun showDropDownMenu(view: View, item: CartListItemModel) {
        PopupMenu(view.context, view).apply {
            menuInflater.inflate(R.menu.menu_item_product_quantity, menu)
            setOnMenuItemClickListener {
                viewEventDelegate.onViewEvent(CartProductQuantityEventData(item.id, it.title.toString().toInt()))
                true
            }
            show()
        }
    }
}

class CartProductViewHolder(
    private val binding: ItemCartProductBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CartListItemModel) {
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

data class CartProductQuantityEventData(
    val id: Long,
    val count: Int
) : ViewEventContext

data class CartProductRemoveEventData(
    val id: Long
) : ViewEventContext