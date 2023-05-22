package com.linkdom.readbook

import android.content.Context
import android.widget.Toast

fun makeToast(context: Context, s: String) {
    Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
}