package com.sergeikuchin.asambeauty.domain.delegates

import com.sergeikuchin.asambeauty.domain.mappers.ProductToCartMapper
import com.sergeikuchin.asambeauty.domain.mappers.ProductToWishMapper
import com.sergeikuchin.asambeauty.domain.mappers.WishToCartMapper
import com.sergeikuchin.asambeauty.domain.models.*
import com.sergeikuchin.asambeauty.utils.replace

private const val CART_WISH_MAX_ITEM_COUNT = 9

class HomeViewModelDelegateImpl(
    private val productToWishMapper: ProductToWishMapper,
    private val wishToCartMapper: WishToCartMapper,
    private val productToCartMapper: ProductToCartMapper
) : HomeViewModelDelegate {

    override fun addProductToWishList(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>> {
        val product = getProduct(productId, products) ?: return cartWishListModel to products
        val currentWishListModel = cartWishListModel?.wishListModel
        val currentWishListItemModel =
            cartWishListModel?.wishListModel?.wishItems?.find { it.id == productId }

        val wishListProduct = productToWishMapper.map(
            product,
            currentWishListItemModel,
        ) ?: return cartWishListModel to products

        val newWishListModel = currentWishListModel?.copy(
            totalPrice = currentWishListModel.totalPrice + product.price,
            wishItems = mutableListOf(wishListProduct).apply {
                addAll(
                    currentWishListModel.wishItems
                        .toMutableList()
                        .apply { remove(currentWishListItemModel) }
                        .take(CART_WISH_MAX_ITEM_COUNT)
                )
            }
        ) ?: WishListModel(
            totalPrice = product.price,
            wishItems = listOf(wishListProduct)
        )

        val updatedProducts = products
            .toMutableList()
            .replace(product, product.copy(addedToWishList = true))

        return (cartWishListModel?.copy(
            wishListModel = newWishListModel
        ) ?: CartWishListModel(
            wishListModel = newWishListModel,
            cartListModel = null
        )) to updatedProducts
    }

    override fun addProductToCartList(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>> {
        val product = getProduct(productId, products) ?: return cartWishListModel to products
        val currentCartListModel = cartWishListModel?.cartListModel
        val currentCartListItemModel = currentCartListModel?.cartItems?.find { it.id == productId }

        val cartListProduct = productToCartMapper.map(
            product,
            currentCartListItemModel,
        ) ?: return cartWishListModel to products

        val newCartListModel = currentCartListModel?.copy(
            totalPrice = currentCartListModel.totalPrice + product.price,
            cartItems = mutableListOf(cartListProduct).apply {
                addAll(
                    currentCartListModel.cartItems
                        .toMutableList()
                        .apply { remove(currentCartListItemModel) }
                        .take(CART_WISH_MAX_ITEM_COUNT)
                )
            }
        ) ?: CartListModel(
            totalPrice = product.price,
            cartItems = listOf(cartListProduct)
        )

        val updatedProducts = products
            .toMutableList()
            .replace(product, product.copy(addedToCartList = true))

        return (cartWishListModel?.copy(
            cartListModel = newCartListModel
        ) ?: CartWishListModel(
            wishListModel = null,
            cartListModel = newCartListModel
        )) to updatedProducts
    }

    override fun addWishToCartList(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>> {
        val wish =
            getWishProduct(productId, cartWishListModel) ?: return cartWishListModel to products
        val currentCartListModel = cartWishListModel?.cartListModel
        val currentWishListModel = cartWishListModel?.wishListModel
        val currentCartListItemModel = currentCartListModel?.cartItems?.find { it.id == productId }
        val cartListProduct = wishToCartMapper.map(wish)

        val newCartListModel = currentCartListModel?.copy(
            totalPrice = currentCartListModel.totalPrice + (wish.price * wish.count),
            cartItems = mutableListOf(cartListProduct).apply {
                addAll(
                    currentCartListModel.cartItems
                        .toMutableList()
                        .apply { remove(currentCartListItemModel) }
                        .take(CART_WISH_MAX_ITEM_COUNT)
                )
            }
        ) ?: CartListModel(
            totalPrice = wish.price,
            cartItems = listOf(cartListProduct)
        )

        val newWishListModel = currentWishListModel?.copy(
            totalPrice = currentWishListModel.totalPrice - (wish.price * wish.count),
            wishItems = currentWishListModel.wishItems
                .toMutableList()
                .apply { remove(wish) }
                .take(CART_WISH_MAX_ITEM_COUNT)
        )

        val product = getProduct(productId, products)
        val updatedProducts = product?.let { oldProduct ->
            products.toMutableList().replace(
                oldProduct,
                oldProduct.copy(
                    addedToWishList = false,
                    addedToCartList = true
                )
            )
        } ?: emptyList()

        return (cartWishListModel?.copy(
            cartListModel = newCartListModel,
            wishListModel = newWishListModel.takeIf {
                it?.wishItems?.isNullOrEmpty()?.not() == true
            }
        )) to updatedProducts
    }

    override fun removeFromCart(
        productId: Long,
        cartWishListModel: CartWishListModel?,
        products: List<ProductModel>
    ): Pair<CartWishListModel?, List<ProductModel>> {
        val cartProduct =
            getCartProduct(productId, cartWishListModel) ?: return cartWishListModel to products
        val currentCartListModel = cartWishListModel?.cartListModel

        val newCartListModel = currentCartListModel?.copy(
            totalPrice = currentCartListModel.totalPrice - (cartProduct.price * cartProduct.count),
            cartItems = currentCartListModel.cartItems.toMutableList().apply { remove(cartProduct) }
        )

        val product = getProduct(productId, products)
        val updatedProducts = product?.let { oldProduct ->
            products.toMutableList().replace(
                oldProduct,
                oldProduct.copy(
                    addedToCartList = false
                )
            )
        } ?: emptyList()

        return (cartWishListModel?.copy(
            cartListModel = newCartListModel.takeIf {
                it?.cartItems?.isNullOrEmpty()?.not() == true
            }
        )) to updatedProducts
    }

    override fun changeWishProductCount(
        productId: Long,
        count: Int,
        cartWishListModel: CartWishListModel?
    ): CartWishListModel? {
        val wishProduct = getWishProduct(productId, cartWishListModel) ?: return cartWishListModel
        val updatedWishProduct = wishProduct.copy(
            count = count
        )

        val newWishListModel = cartWishListModel?.wishListModel?.copy(
            wishItems = cartWishListModel.wishListModel.wishItems
                .toMutableList()
                .replace(wishProduct, updatedWishProduct),
            totalPrice = cartWishListModel.wishListModel.totalPrice + ((count - wishProduct.count) * wishProduct.price)
        )

        return cartWishListModel?.copy(
            wishListModel = newWishListModel
        )
    }

    override fun changeCartProductCount(
        productId: Long,
        count: Int,
        cartWishListModel: CartWishListModel?
    ): CartWishListModel? {
        val cartProduct = getCartProduct(productId, cartWishListModel) ?: return cartWishListModel
        val updatedCartProduct = cartProduct.copy(
            count = count
        )

        val newCartListModel = cartWishListModel?.cartListModel?.copy(
            cartItems = cartWishListModel.cartListModel.cartItems
                .toMutableList()
                .replace(cartProduct, updatedCartProduct),
            totalPrice = cartWishListModel.cartListModel.totalPrice + ((count - cartProduct.count) * cartProduct.price)
        )

        return cartWishListModel?.copy(
            cartListModel = newCartListModel
        )
    }

    private fun getProduct(productId: Long, products: List<ProductModel>): ProductModel? =
        products.find { it.id == productId }

    private fun getWishProduct(
        productId: Long,
        cartWishListModel: CartWishListModel?
    ): WishListItemModel? =
        cartWishListModel?.wishListModel?.wishItems?.find { it.id == productId }

    private fun getCartProduct(
        productId: Long,
        cartWishListModel: CartWishListModel?
    ): CartListItemModel? =
        cartWishListModel?.cartListModel?.cartItems?.find { it.id == productId }
}