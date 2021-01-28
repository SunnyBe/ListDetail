package com.buchi.listdetail.data.repository

import com.buchi.listdetail.utils.OkHttpProvider

class MainRepositoryImpl constructor(
    private val networkClient: OkHttpProvider = OkHttpProvider
) {

    fun allList() {
        val response = networkClient.makeNetworkRequest("user?limit=100")
    }

}