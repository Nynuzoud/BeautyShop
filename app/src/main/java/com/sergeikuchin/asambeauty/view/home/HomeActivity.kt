package com.sergeikuchin.asambeauty.view.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.sergeikuchin.asambeauty.R
import com.sergeikuchin.asambeauty.databinding.ActivityHomeBinding
import com.sergeikuchin.asambeauty.domain.models.CartWishListModel
import com.sergeikuchin.asambeauty.domain.models.CategoryTitleModel
import com.sergeikuchin.asambeauty.domain.models.ProductModel
import com.sergeikuchin.asambeauty.view.home.adapters.*
import com.sergeikuchin.asambeauty.view.utils.ViewEventContext
import com.sergeikuchin.asambeauty.view.utils.ViewEventDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity(), ViewEventDelegate {

    private val cartWishListsAdapter = CartWishListsAdapter(this)
    private val popularProductsCategoryTitleAdapter = PopularProductsCategoryTitleAdapter()
    private val homeProductAdapter = HomeProductAdapter(this)

    private val homeViewModel: HomeViewModel by viewModel()

    private var binding: ActivityHomeBinding? = null

    override fun onViewEvent(viewEventContext: ViewEventContext) {
        when (viewEventContext) {
            is ProductWishEventData -> homeViewModel.addToWishList(viewEventContext.id)
            is ProductCartEventData -> homeViewModel.addToCartList(viewEventContext.id)
            is WishProductAddToCartEventData -> homeViewModel.addToCartFromWishList(viewEventContext.id)
            is CartProductRemoveEventData -> homeViewModel.removeFromCart(viewEventContext.id)
            is WishProductQuantityEventData -> homeViewModel.changeWishProductCount(viewEventContext.id, viewEventContext.count)
            is CartProductQuantityEventData -> homeViewModel.changeCartProductCount(viewEventContext.id, viewEventContext.count)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupRecyclerView()
        setupSubscriptions()
    }

    private fun setupRecyclerView() {
        binding?.mainRecyclerView?.layoutManager = FlexboxLayoutManager(this).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.CENTER
            flexWrap = FlexWrap.WRAP

        }
        binding?.mainRecyclerView?.adapter =
            ConcatAdapter(
                cartWishListsAdapter,
                popularProductsCategoryTitleAdapter,
                homeProductAdapter
            )


    }

    private fun setupSubscriptions() {
        homeViewModel.subscribeOnCartWishListModels()
            .observe(this, ::updateCartWishListAdapter)
        homeViewModel.subscribeOnProductModels()
            .observe(this, ::updateProductListAdapter)
    }

    private fun updateCartWishListAdapter(cartWishListModel: CartWishListModel?) {
        cartWishListsAdapter.items = cartWishListModel?.let { listOf(it) } ?: emptyList()
    }

    private fun updateProductListAdapter(products: List<ProductModel>) {
        homeProductAdapter.items = products

        popularProductsCategoryTitleAdapter.items = listOf(
            CategoryTitleModel(
                getString(
                    R.string.category_title_popular_products
                )
            )
        )
    }
}