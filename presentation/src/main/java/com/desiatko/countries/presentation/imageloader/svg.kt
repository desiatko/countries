package com.desiatko.countries.presentation.imageloader

import android.content.Context
import android.graphics.drawable.PictureDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.Options
import com.bumptech.glide.load.ResourceDecoder
import com.bumptech.glide.load.engine.Resource
import com.bumptech.glide.load.resource.SimpleResource
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder
import com.bumptech.glide.module.AppGlideModule
import com.larvalabs.svgandroid.SVG
import com.larvalabs.svgandroid.SVGBuilder
import com.larvalabs.svgandroid.SVGParseException
import java.io.IOException
import java.io.InputStream


/**
 * Taken from https://github.com/bumptech/glide/tree/master/samples/svg/
 */
class SvgDecoder : ResourceDecoder<InputStream, SVG> {

    override fun handles(source: InputStream, options: Options): Boolean {
        return true
    }

    @Throws(IOException::class)
    override fun decode(
        source: InputStream, width: Int, height: Int,
        options: Options
    ): Resource<SVG> {
        try {
            val svg = SVGBuilder().readFromInputStream(source).build()
            return SimpleResource<SVG>(svg)
        } catch (ex: SVGParseException) {
            throw IOException("Cannot load SVG from stream", ex)
        }

    }
}

class SvgDrawableTranscoder : ResourceTranscoder<SVG, PictureDrawable> {
    override fun transcode(
        toTranscode: Resource<SVG>,
        options: Options
    ): Resource<PictureDrawable>? {
        val svg = toTranscode.get()
        return SimpleResource(svg.drawable)
    }
}

@GlideModule
class SvgModule : AppGlideModule() {
    override fun registerComponents(
        context: Context, glide: Glide,
        registry: Registry
    ) {
        registry.register(SVG::class.java, PictureDrawable::class.java, SvgDrawableTranscoder())
            .append(InputStream::class.java, SVG::class.java, SvgDecoder())
    }

    // Disable manifest parsing to avoid adding similar modules twice.
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}