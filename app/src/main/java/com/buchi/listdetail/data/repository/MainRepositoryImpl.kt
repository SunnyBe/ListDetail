package com.buchi.listdetail.data.repository

import android.content.Context
import android.util.Log
import com.buchi.listdetail.data.cache.UserDao
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.presentation.MainViewState
import com.buchi.listdetail.utils.OkHttpProvider
import com.buchi.listdetail.utils.ResultState
import com.buchi.listdetail.utils.ReusableFlow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl constructor(
    private val networkClient: OkHttpProvider = OkHttpProvider,
    private val userDao: UserDao,
    private val context: Context
) : MainRepository {

    private val reusableFlow: ReusableFlow<MainViewState> by lazy {
        ReusableFlow(context)
    }

    fun allLists(): Flow<ResultState<MainViewState>> {
        return reusableFlow.internetCheckProcessFlow {
            val response = networkClient.makeNetworkRequest("user?limit=100")
            val allUserResponse = if (response.isSuccessful) {
                response.body?.string()?.let {
                    networkClient.stringToObject<MainEntity.ApiResponse<List<MainEntity.User>>>(
                        it
                    )
                }
            } else null
            MainViewState(allUser = allUserResponse?.data)
        }.flowOn(Dispatchers.IO)
    }

    override fun allList(): Flow<ResultState<MainViewState>> {
        return reusableFlow.updateCacheBeforeSync(object :
            ReusableFlow.CacheSynchProcess<MainViewState> {
            // Emit cached list if not empty
            override fun checkCache(): MainViewState {
                val cachedUsers = userDao.getAll()
                return if (cachedUsers.isNotEmpty()) {
                    MainViewState(allUser = cachedUsers)
                } else {
                    MainViewState()
                }
            }

            // Synch with data from network
            override fun synchFromNetwork(): MainViewState {
                val response = networkClient.makeNetworkRequest("users")
                val allUserResponse = if (response.isSuccessful) {
                    response.body?.string()?.let {
                        val typeToken = object : TypeToken<List<MainEntity.User>>() {}.type
                        Gson().fromJson<List<MainEntity.User>>(it, typeToken)
                    }
                } else null
                // Update the cache with a new list
                updateUserListInCache(allUserResponse)
                return MainViewState(allUser = allUserResponse)
            }

        }).flowOn(Dispatchers.IO)
    }

    override fun userDetail(userId: Int?): Flow<ResultState<MainViewState>> {
        return reusableFlow.internetCheckProcessFlow {
            val response = networkClient.makeNetworkRequest("users/$userId")
            val userDetailResponse = response.body?.string()?.let {
                networkClient.stringToObject<MainEntity.User>(it)
            }
            MainViewState(null, userDetailResponse)
        }
            .flowOn(Dispatchers.IO)
    }

    fun updateUserListInCache(networkList: List<MainEntity.User>?) {
        networkList?.let { list->
            Log.d(javaClass.simpleName, "Updating Cache: $list")
            userDao.insertAll(*list.toTypedArray())
        }
    }

}