package com.sergeikuchin.asambeauty.domain.usecases

import com.sergeikuchin.asambeauty.domain.ResultStatus
import com.sergeikuchin.asambeauty.domain.UseCase
import com.sergeikuchin.asambeauty.domain.models.ImageMedia
import com.sergeikuchin.asambeauty.domain.models.ProductModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject

class GetHomeListUseCaseImpl : GetHomeListUseCase, UseCase<GetHomeListQuery>() {

    private val homeModels = BehaviorSubject.create<GetHomeListResult>()

    override fun getHomeList(): Observable<GetHomeListResult> = homeModels.hide()
        .subscribeOn(Schedulers.io())
        .also {
            getData()
        }

    private fun getData() {
        homeModels.onNext(
            GetHomeListResult(
                cartWishListModel = null,
                productModels = getProducts(),
                status = ResultStatus.Success
            )
        )
    }

}

fun getProducts(): List<ProductModel> = listOf(
    ProductModel(
        id = 241,
        title = "VINO GOLD Beinwell Gel",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/4/8/480ba696343d472600ec636b684ec99d42c47e6e_1.jpg"),
        price = 19.75f,
        oldPrice = 22.95f,
    ),
    ProductModel(
        id = 242,
        title = "VITAMIN C 3-Minutes Peeling Maske",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/d/e/de635810c7f7379a3926095393b27829ac5d539d_1.jpg"),
        price = 18.95f,
    ),
    ProductModel(
        id = 243,
        title = "PERFECT SKIN Tan Yourself! Selbstbräuner Mousse & Applikator-Handschuh",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/b/b/bb8b6b6deb53f2fbabf4a73dc6156ade32ee1db4_1.jpg"),
        price = 24.75f,
        oldPrice = 29.95f,
    ),
    ProductModel(
        id = 244,
        title = "AHA Intensive Treatment",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/2/e/2ee2c6d5c6e4cc29146e1bd9e268bea301741b73_1.jpg"),
        price = 24.95f,
    ),
    ProductModel(
        id = 245,
        title = "Touch of Summer Eau de Parfum",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/4/e/4eb791e8ac9bb87960770f2076cfce42aa00e5eb_1.jpg"),
        price = 24.75f,
    ),
    ProductModel(
        id = 246,
        title = "COLOR CARE Haarmaske für gefärbte Haare",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/8/0/8092c0463d81e91ad55555a111f9a76bb2423c49_1.jpg"),
        price = 15.95f,
        oldPrice = 19.95f,
    ),
    ProductModel(
        id = 247,
        title = "Blue Agave Intensive Care",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/3/5/359a1637bb8393da1ae7ac50b332385bd82c121a_1.jpg"),
        price = 17.75f,
    ),
    ProductModel(
        id = 248,
        title = "COLLAGEN LIFT 2 Phasen LIFTING AMPULLEN",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/6/6/666d90cb4132a00005081b9ada94708c4b6c2c15_1.jpg"),
        price = 21.99f,
        oldPrice = 24.75f,
    ),
    ProductModel(
        id = 249,
        title = "ADVANCED Stem Cell Serum",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/a/5/a5d5bc18e6157fcf1aea8ba713778b0f858c5ca9_1.jpg"),
        price = 29.75f,
    ),
    ProductModel(
        id = 250,
        title = "HYALURON REPAIR All Over Therapy Butter",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/9/6/96f2e5f6bbbef0773343e0453e43728a5ddca1d3_1.jpg"),
        price = 16.95f,
    ),
    ProductModel(
        id = 251,
        title = "MAGIC FINISH Make-up Remover",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/e/e/eef428db6968ea3e841c12c5c5191e0ddef71cc2_1.jpg"),
        price = 19.95f,
    ),
    ProductModel(
        id = 252,
        title = "VITAMIN C Renew Handcreme",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/7/9/7970898055a79e8bfd02062697db51978fb4499a_1.jpg"),
        price = 9.95f,
    ),
    ProductModel(
        id = 253,
        title = "KERATIN Rebuild Quick Mask",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/3/6/36a91c74b73f9a78612a2e60bd0707f402e8d8b3_1.jpg"),
        price = 17.75f,
        oldPrice = 22.95f,
    ),
    ProductModel(
        id = 254,
        title = "MAGIC FINISH Make-up Summer Teint mit LSF 30",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/f/f/ff9b5955ec67d0a7b2d9891ccdf0ee9886eec677_1.jpg"),
        price = 24.75f,
    ),
    ProductModel(
        id = 255,
        title = "PERFECT SKIN Musselin Reinigungstücher",
        image = ImageMedia("https://images.asambeauty.com/media/catalog/product/cache/d8e9e7aaeaeb149d22df7469c4346444/1/4/14b04ec080ed2c030d1e9abe2f24b355b09dfcbd_1.jpg"),
        price = 19.95f,
    )
)