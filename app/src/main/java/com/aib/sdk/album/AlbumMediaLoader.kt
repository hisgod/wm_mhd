package com.aib.sdk.album

import android.widget.ImageView

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wm.collector.R
import com.yanzhenjie.album.AlbumFile
import com.yanzhenjie.album.AlbumLoader


/**
 * Album库图片加载器
 */
class AlbumMediaLoader : AlbumLoader {
    override fun load(imageView: ImageView, albumFile: AlbumFile) {
        load(imageView, albumFile.path)
    }

    override fun load(imageView: ImageView, url: String) {
        Glide.with(imageView.context)
                .load(url)
                .apply(RequestOptions().placeholder(R.drawable.ic_img_loading).error(R.drawable.ic_img_error))
                .into(imageView)
    }
}
