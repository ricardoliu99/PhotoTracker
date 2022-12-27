package com.example.phototracker.ui.adapters

import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import coil.load
import com.example.phototracker.R

@BindingAdapter("imageViewUri")
fun bindImageView(imgView: ImageView, uri: String?) {
    uri?.let {
        val imgUri = uri.toUri()
            .buildUpon()
            .build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.broken_img)
        }
    }
}

@BindingAdapter("imageButtonUri")
fun bindImageButton(imgButton: ImageButton, uri: String?) {
    uri?.let {
        val imgUri = uri.toUri()
            .buildUpon()
            .build()
        imgButton.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.broken_img)
        }
    }
}