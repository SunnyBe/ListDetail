package com.buchi.listdetail.utils

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

/**
 * Created by Sunday Ndu<ndusunday@gmail.com>
 * A Reusable flow utility class to simplify and reduce rewriting of this process for all repository
 * functions. Different possible condition a covered
 */
class ReusableFlow<T>(private val context: Context) {

    /**
     * A Reusable flow function to simplify network checks before fetching from API
     * @param processFetchAction lambda function to process API fetching. The response from this is
     * emitted as through the flow.
     */
    fun internetCheckProcessFlow(processFetchAction: () -> T): Flow<ResultState<T>> {
        return flow<ResultState<T>> {
            if (InternetReachability.isInternetAvailable(context)) {
                val apiRequest = processFetchAction()
                emit(ResultState.data(null, apiRequest))
            } else {
                emit(ResultState.error("Please check your internet connection"))
            }
        }.onStart {
            emit(ResultState.loading(true))
        }
            .catch { ex ->
                emit(ResultState.error(ex.message ?: ""))
            }
    }

    fun <K> updateListWithCacheBeforeSync(
        dao: GenericDao<K>,
        processListFetch: (daoToUpdateCache: GenericDao<K>) -> T
    ): Flow<ResultState<T>> {

        return flow<ResultState<T>> {
            val cachedData = dao.getAll()
            if (cachedData.isNotEmpty()) {
                ResultState.data(null, cachedData)
            }
            if (InternetReachability.isInternetAvailable(context)) {
                val apiRequest = processListFetch(dao)
                emit(ResultState.data(null, apiRequest))
            } else {
                emit(ResultState.error("Please check your internet connection"))
            }
        }.onStart {
            emit(ResultState.loading(true))
        }
            .catch { ex ->
                emit(ResultState.error(ex.message ?: ""))
            }
    }

//    fun receiveFlowUpdateDb(flow: Flow<ResultState<T>>, processFetchAction: () -> T): Flow<ResultState<T>> {
//        return flow<ResultState<T>> {
//            if (InternetReachability.isInternetAvailable(context)) {
//                val apiRequest = processFetchAction()
//                emit(ResultState.data(null, apiRequest))
//            } else {
//                emit(ResultState.error("Please check your internet connection"))
//            }
//        }.onStart {
//            emit(ResultState.loading(true))
//        }
//            .catch { ex ->
//                emit(ResultState.error(ex.message ?: ""))
//            }
//    }
}