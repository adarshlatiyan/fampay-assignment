package com.adarsh.dynamicui.util

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import kotlin.math.roundToInt

fun Context.openUrl(url: String?) {
    url?.let {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        applicationContext.startActivity(intent)
    }
}

fun Context.dpToPixel(dp: Int): Int {
    val scale = resources.displayMetrics.density
    return (dp * scale).roundToInt()
}