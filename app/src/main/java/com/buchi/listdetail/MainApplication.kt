package com.buchi.listdetail

import android.app.Application

open class MainApplication : Application() {

    open fun getBaseUrl(): String {
        return "http://apiurl"
    }

}