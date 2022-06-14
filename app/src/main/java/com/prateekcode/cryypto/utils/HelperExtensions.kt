package com.prateekcode.cryypto.utils

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.prateekcode.cryypto.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.concurrent.TimeUnit

fun RecyclerView.init(
    mContext: Context,
    mAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    decoration: RecyclerView.ItemDecoration? = null,
    isDecorated: Boolean = false,
    enableNestedScrolling: Boolean = false,
    isFixedSize: Boolean = true,
    manager: RecyclerView.LayoutManager = LinearLayoutManager(mContext)
) {
    apply {
        setHasFixedSize(isFixedSize)
        if (!enableNestedScrolling)
            ViewCompat.setNestedScrollingEnabled(this, enableNestedScrolling)
        layoutManager = manager
        if (decoration == null) {
            if (isDecorated)
                addItemDecoration(DividerItemDecoration(mContext, RecyclerView.VERTICAL))
        } else
            addItemDecoration(decoration)
        adapter = mAdapter
    }
}

fun RecyclerView.init(
    mContext: Context,
    decoration: RecyclerView.ItemDecoration? = null,
    isDecorated: Boolean = false,
    enableNestedScrolling: Boolean = false,
    isFixedSize: Boolean = true,
    manager: RecyclerView.LayoutManager = LinearLayoutManager(mContext)
) {
    apply {
        setHasFixedSize(isFixedSize)
        if (!enableNestedScrolling)
            ViewCompat.setNestedScrollingEnabled(this, enableNestedScrolling)
        layoutManager = manager
        if (decoration == null) {
            if (isDecorated)
                addItemDecoration(DividerItemDecoration(mContext, RecyclerView.VERTICAL))
        } else
            addItemDecoration(decoration)
    }
}

fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.ic_launcher_foreground)
        .error(R.drawable.ic_launcher_foreground)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun EditText.getStringInput(): String {
    return text?.trim()?.toString() ?: ""
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

fun EditText.onChange(textChanged: ((String) -> Unit)) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            textChanged.invoke(s.toString())
        }
    })
}


fun Context.showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this, it, length).show()
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                return true
            }
        }
    } else {
        try {
            val activeNetworkInfo = manager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        } catch (e: Exception) {
            e.showLog()
        }
    }
    return false
}

/**
 * Extension method to run block of code after specific Delay.
 */
fun runDelayed(delay: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS, action: () -> Unit) {
    Handler().postDelayed(action, timeUnit.toMillis(delay))
}


fun Double.roundOffDecimal(): Double {
    return DecimalFormat("#.###").apply {
        roundingMode = RoundingMode.FLOOR
    }.format(this).toDouble()
}


fun TextView.setDrawableColor(color: Int) {
    for (drawable in this.compoundDrawablesRelative) {
        drawable?.mutate()
        drawable?.colorFilter = PorterDuffColorFilter(
            color, PorterDuff.Mode.SRC_IN
        )
    }
}

fun ImageView.setImage(
    imageUrl: String?,
    placeHolder: Int = R.drawable.cryptocurrency_small
) {
    try {
        Glide.with(this).applyDefaultRequestOptions(
            RequestOptions().placeholder(placeHolder).error(placeHolder)
        ).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().into(this)
    } catch (ex: Exception) {
        ex.showLog()
    }
}