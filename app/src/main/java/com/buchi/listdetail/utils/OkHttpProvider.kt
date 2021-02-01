package com.buchi.listdetail.utils

import android.util.Log
import com.buchi.listdetail.BuildConfig
import com.buchi.listdetail.data.model.MainEntity
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

object OkHttpProvider {
    var baseUrl: String? = null

    init {
//        baseUrl = "https://dummyapi.io/data/api/"
        baseUrl = "https://jsonplaceholder.typicode.com/"
    }

    // Timeout for the network requests
    private val REQUEST_TIMEOUT = 30L

    private var okHttpClient: OkHttpClient? = null

    fun getOkHttpClient(): OkHttpClient {
        return if (okHttpClient == null) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .build()
            this.okHttpClient = okHttpClient
            okHttpClient
        } else {
            okHttpClient!!
        }
    }

    /**
     * Makes an instance of the request builder using the specified url and provided request body.
     * based on the verb of api request to be made.
     * @param url of the api request to be made. Should include the base url.
     * @param requestBody to specify POST request with the specified body. If not passed, default is a
     * GET request.
     * @return instance of the created request builder.
     */
    private fun buildRequest(url: String, requestBody: RequestBody? = null): Request.Builder {
        Log.d(javaClass.simpleName, "Request url: $url")
        val request = if (requestBody != null) {
            Request.Builder()
                .url(url)
                .post(requestBody)
        } else {
            Request.Builder().url(url).get()
        }
        // AppID is meant to be secret and will be passed from pipeline vie gradle.properties file.
//        request.addHeader("app-id", BuildConfig.APP_ID)
        request.addHeader("Content-type", "application/json")
        return request
    }

    /**
     * Make Network request.
     * @param url Endpoint for the network request.
     * @param body of the request in-case of a POST request. param can be NULL.
     * @return api response for the request.
     */
    fun makeNetworkRequest(url: String, body: RequestBody? = null): Response {
        val cUrl = appendUrl(url) ?: ""
        val request = buildRequest(cUrl, body).build()
        Log.d(javaClass.simpleName, "Request: $request ==> Body: ${request.body}")
        val response = getOkHttpClient().newCall(request = request).execute()
        Log.d(javaClass.simpleName, "Response Code: ${response.code}")
        return response
    }


    /**
     * Append base url with the extra url together to make a complete url.
     * @param extendedUrl additional url appended to the base url.
     * @return complete url for the endpoint.
     */
    fun appendUrl(extendedUrl: String?): String? {
        return if (baseUrl != null && extendedUrl != null) {
            baseUrl + extendedUrl
        } else if (baseUrl != null && extendedUrl == null) {
            baseUrl
        } else {
            null
        }
    }

    inline fun<reified T> stringToObject(jsonString: String): T {
        return Gson().fromJson(jsonString, T::class.java)
    }

}