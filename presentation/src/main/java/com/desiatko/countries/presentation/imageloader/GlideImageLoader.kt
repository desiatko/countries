package com.desiatko.countries.presentation.imageloader

import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide

object GlideImageLoader : ImageLoader {

    override fun loadImage(url: String, target: ImageView): ImageLoader.Cancelable {
        val context = target.context.applicationContext

        Glide.with(context)
            .load(Uri.parse(url))
            .into(target)

        return object : ImageLoader.Cancelable {
            override fun cancel() {
                Glide.with(context).clear(target)
            }
        }
    }

}