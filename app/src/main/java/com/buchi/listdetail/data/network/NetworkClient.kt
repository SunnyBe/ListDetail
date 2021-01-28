package com.buchi.listdetail.data.network

import android.util.Log
import com.buchi.listdetail.utils.OkHttpProvider
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class NetworkClient constructor(
    val baseUrl: String
) {

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpProvider.getOkHttpClient()
    }

    /**
     * Append url to the baseUrl used in creating an instance of this class and return request builder
     * based on the verb of api request to be made.
     * @param url of the api request to make a complete endpoint when appended to baseUrl.
     * @param requestBody to specify POST request with the specified body. If not passed, default is
     * GET request.
     */
    private fun appendUrl(url: String, requestBody: RequestBody? = null): Request.Builder {
        val newUrl = baseUrl + url
        Log.d(javaClass.simpleName, "Request url: $newUrl")
        return if (requestBody != null) {
            Request.Builder()
                .url(newUrl)
                .post(requestBody)
        } else {
            Request.Builder().url(newUrl).get()
        }
    }

    fun makeRequest(url: String, body: RequestBody? = null): Response {
        val request = appendUrl(url, body).build()
        Log.d(javaClass.simpleName, "Request: $request\nBody: ${request.body}")
        return okHttpClient.newCall(request = request).execute()
    }
}