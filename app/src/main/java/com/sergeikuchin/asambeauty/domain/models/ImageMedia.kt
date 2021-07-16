package com.sergeikuchin.asambeauty.domain.models

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

data class ImageMedia(
    val url: String
) {

    fun loadInto(
        imageView: AppCompatImageView,
    ) {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}