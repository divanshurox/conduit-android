package com.example.conduit.extensions

import android.annotation.SuppressLint
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("ConstantLocale")
val isoFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
@SuppressLint("ConstantLocale")
val appDateFormat = SimpleDateFormat("dd,MMMM yyyy",Locale.getDefault())

fun TextView.showFormattedDate(timestamp: String){
    val date = isoFormatter.parse(timestamp)
    text = appDateFormat.format(date!!)
}