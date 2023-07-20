package org.jash.common

import android.util.Log

val Any.TAG
    get() = javaClass.simpleName

fun Any.logDebug(a:Any?) {
    Log.d(TAG, a.toString())
}