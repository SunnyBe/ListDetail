package com.buchi.listdetail.data.repository

import com.buchi.listdetail.presentation.MainViewState
import com.buchi.listdetail.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun allList(): Flow<ResultState<MainViewState>>
    fun userDetail(userId: Int?): Flow<ResultState<MainViewState>>
}