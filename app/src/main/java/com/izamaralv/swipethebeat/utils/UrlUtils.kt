package com.izamaralv.swipethebeat.utils

import android.content.Intent
import android.net.Uri

fun navigateToUrl(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}