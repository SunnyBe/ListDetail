package com.buchi.listdetail

import android.app.Application
import com.buchi.listdetail.data.cache.MainDatabase
import com.buchi.listdetail.data.repository.MainRepository
import com.buchi.listdetail.data.repository.MainRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

open class MainApplication : Application() {

    open fun getBaseUrl(): String {
        return "http://apiurl"
    }

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { MainDatabase.getDatabase(this, applicationScope) }
    val repository: MainRepository by lazy {
        MainRepositoryImpl(
            userDao = database.userDao(),
            context = applicationContext
        )
    }

}