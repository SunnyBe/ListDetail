package com.buchi.listdetail.data.repository

import android.content.Context
import com.buchi.listdetail.data.cache.UserDao
import com.buchi.listdetail.data.model.MainEntity
import com.buchi.listdetail.presentation.MainViewState
import com.buchi.listdetail.utils.OkHttpProvider
import com.buchi.listdetail.utils.ResultState
import com.buchi.listdetail.utils.ReusableFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

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
        return reusableFlow.updateListWithCacheBeforeSync(userDao) { daoToUpdateCache ->
            val response = networkClient.makeNetworkRequest("user?limit=100")
            val allUserResponse = if (response.isSuccessful) {
                response.body?.string()?.let {
                    networkClient.stringToObject<MainEntity.ApiResponse<List<MainEntity.User>>>(
                        it
                    )
                }
            } else null

            // Update the cache with a new list
            allUserResponse?.data?.map {
                daoToUpdateCache.insertAll(it)
            }
            MainViewState(allUser = allUserResponse?.data)
        }
    }

    override fun userDetail(userId: String?): Flow<ResultState<MainViewState>> {
        return flow {
            emit(ResultState.data(null, MainViewState()))
        }.onStart {
            emit(ResultState.loading(true))
        }.catch {
            it.printStackTrace()
            emit(ResultState.error(it.message ?: ""))
        }
            .flowOn(Dispatchers.IO)
    }

}