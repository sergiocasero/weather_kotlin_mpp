package com.sergiocasero.weather.ui.extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.sergiocasero.weather.R

/**
 * ViewExtensions.
 */
fun ImageView.loadIcon(id: String) {
    Glide.with(this)
        .load("https://openweathermap.org/img/wn/$id@2x.png")
        .into(this)
}

/**
 * View
 * */
fun View.hideMe(gone: Boolean = true) {
    this.visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.showMe() {
    this.visibility = View.VISIBLE
}

fun Context.retrySnackbar(
    container: View,
    message: String,
    retryCallback: () -> Unit = {}
) {
    val color = ContextCompat.getColor(this, R.color.white)
    val snackbar = Snackbar.make(container, message, Snackbar.LENGTH_INDEFINITE)
        .setActionTextColor(color)

    snackbar.setAction(R.string.retry) { retryCallback() }

    snackbar.view.setBackgroundResource(R.color.red_800)
    snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(color)
    snackbar.show()
}

fun ViewGroup.animateChild() {
    TransitionManager.beginDelayedTransition(this)
}
