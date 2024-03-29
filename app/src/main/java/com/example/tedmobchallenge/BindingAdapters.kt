package com.example.tedmobchallenge

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.tedmobchallenge.domain.Product
import com.example.tedmobchallenge.screens.home.ProductListAdapter
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("productListData")
fun bindMovieRecyclerView(recyclerView: RecyclerView, data: List<Product>?) {
    val adapter = recyclerView.adapter as ProductListAdapter
    adapter.submitList(data)
}

@BindingAdapter("username")
fun bindUsername(textView: TextView, username: String?) {
    textView.text = username ?: ""
}


@BindingAdapter("productTitle")
fun bindTitle(textView: TextView, title: String?) {
    textView.text = title ?: ""
}

@BindingAdapter("productImage")
fun bindImageUrl(imageView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = (imgUrl).toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imageView)
    }
}

@BindingAdapter("productPrice")
fun bindPrice(textView: TextView, price: Double?) {
    textView.text = ("$price USD") ?: ""
}

@BindingAdapter("productDetailTitle")
fun bindDetailTitle(textView: TextView, description: String?) {
    var fullText = "<b><i>${textView.context.getString(R.string.title)}</i></b>"
    if (description==null) textView.visibility = View.GONE
    else {
        fullText += " : $description"
        textView.text = Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY)
        textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("productDetailPrice")
fun bindDetailPrice(textView: TextView, price: Double?) {
    var fullText = "<b><i>${textView.context.getString(R.string.price)}</i></b>"
    if (price==null) textView.visibility = View.GONE
    else {
        fullText += " : $price"
        textView.text = Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY)
        textView.visibility = View.VISIBLE
    }
}


@BindingAdapter("productDetailDescription")
fun bindDetailDescription(textView: TextView, description: String?) {
    var fullText = "<b><i>${textView.context.getString(R.string.description)}</i></b>"
    if (description==null) textView.visibility = View.GONE
    else {
        fullText += " : $description"
        textView.text = Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY)
        textView.visibility = View.VISIBLE
    }
}

@BindingAdapter("productDetailCategory")
fun bindDetailCategory(textView: TextView, category: String?) {
    var fullText = "<b><i>${textView.context.getString(R.string.category)}</i></b>"
    if (category==null) textView.visibility = View.GONE
    else {
        fullText += " : $category"
        textView.text = Html.fromHtml(fullText, Html.FROM_HTML_MODE_LEGACY)
        textView.visibility = View.VISIBLE
    }
}

//
@BindingAdapter("loadingSpinner")
fun bindLoad(loadImg: CircularProgressIndicator, data: List<Product>?) {
    if (data.isNullOrEmpty()) {
        loadImg.visibility = View.VISIBLE
    }
    else loadImg.visibility = View.GONE
}

@BindingAdapter("error")
fun bindError(errTxt: TextView, errMsg: String?) {
    when (errMsg) {
        "" -> {
            errTxt.visibility = View.GONE
        }
        else -> {
            errTxt.visibility = View.VISIBLE
            errTxt.text = errMsg
        }
    }
}