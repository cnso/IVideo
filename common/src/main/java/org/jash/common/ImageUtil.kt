package org.jash.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("android:image_url")
fun getImageUrl(image:ImageView, url:String?) {
    if (url != null) {
        Glide.with(image).load(url).into(image)
    }
}