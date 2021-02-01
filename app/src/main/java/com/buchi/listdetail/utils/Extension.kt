package com.buchi.listdetail.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

fun Context.dateOnlyFromIsoDate(timestamp: String, pattern: String): String {
    val df1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
    df1.timeZone = TimeZone.getTimeZone(TimeZone.getDefault().displayName)
    val string1 = timestamp?.replace("\\+0([0-9]){1}\\:00", "+0$100")
    val result1 = df1.parse(string1)

    val timeForm = SimpleDateFormat(pattern, Locale.ENGLISH)
    return timeForm.format(result1)
}