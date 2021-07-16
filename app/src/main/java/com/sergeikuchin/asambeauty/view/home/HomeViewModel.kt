package com.sergeikuchin.asambeauty.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sergeikuchin.asambeauty.domain.delegates.HomeViewModelDelegate
import com.sergeikuchin.asambeauty.domain.models.CartWishListModel
import com.sergeikuchin.asambeauty.domain.models.ProductModel
import com.sergeikuchin.asambeauty.domain.usecases.GetHomeListResult
import com.sergeikuchin.asambeauty.domain.usecases.GetHomeListUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable

class HomeViewModel(
    private val getHomeListUseCase: GetHomeListUseCase,
    private val homeViewModelDelegate: HomeViewModelDelegate
) : ViewModel() {

    private val disposables = CompositeDisposable()

    private val cartWishListModels = MutableLiveData<CartWishListModel?>()
    private val productModels = MutableLiveData<List<ProductModel>>()

    init {
        getHomeList()
    }

    private fun getHomeList() {
        getHomeListUseCase.getHomeList()
            .doOnSubscribe(disposables::add)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(::handleResult)
            .subscribe()
    }

    private fun handleResult(response: GetHomeListResult) {
        cartWishListModels.value = response.cartWishListModel
        productModels.value = response.productModels
    }

    fun subscribeOnCartWishListModels(): LiveData<CartWishListModel?> = cartWishListModels
    fun subscribeOnProductModels(): LiveData<List<ProductModel>> = productModels

    fun addToWishList(productId: Long) {
        val result = homeViewModelDelegate.addProductToWishList(
            productId = productId,
            cartWishListModel = cartWishListModels.value,
            products = productModels.value ?: emptyList()
        )

        cartWishListModels.value = result.first
        productModels.value = result.second
    }

    fun addToCartList(productId: Long) {
        val result = homeViewModelDelegate.addProductToCartList(
            productId = productId,
            cartWishListModel = cartWishListModels.value,
            products = productModels.value ?: emptyList()
        )
        cartWishListModels.value = result.first
        productModels.value = result.second
    }

    fun addToCartFromWishList(productId: Long) {
        val result = homeViewModelDelegate.addWishToCartList(
            productId = productId,
            cartWishListModel = cartWishListModels.value,
            products = productModels.value ?: emptyList()
        )
        cartWishListModels.value = result.first
        productModels.value = result.second
    }

    fun removeFromCart(productId: Long) {
        val result = homeViewModelDelegate.removeFromCart(
            productId = productId,
            cartWishListModel = cartWishListModels.value,
            products = productModels.value ?: emptyList()
        )
        cartWishListModels.value = result.first
        productModels.value = result.second
    }

    fun changeWishProductCount(productId: Long, count: Int) {
        cartWishListModels.value = homeViewModelDelegate.changeWishProductCount(
            productId = productId,
            count = count,
            cartWishListModel = cartWishListModels.value
        )
    }

    fun changeCartProductCount(productId: Long, count: Int) {
        cartWishListModels.value = homeViewModelDelegate.changeCartProductCount(
            productId = productId,
            count = count,
            cartWishListModel = cartWishListModels.value
        )
    }
}