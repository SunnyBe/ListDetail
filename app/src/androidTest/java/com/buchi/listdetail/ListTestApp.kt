package com.buchi.listdetail

class ListTestApp: MainApplication() {

    var url = "http://127.0.0.1:8080"

    override fun getBaseUrl(): String {
        return url
    }
}