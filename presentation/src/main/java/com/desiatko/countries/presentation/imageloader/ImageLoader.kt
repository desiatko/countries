package com.desiatko.countries.presentation.imageloader

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(url: String, target: ImageView): Cancelable

    interface Cancelable {
        fun cancel()
    }
}