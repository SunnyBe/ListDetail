package com.buchi.listdetail.presentation

import com.buchi.listdetail.data.model.MainEntity

/**
 * All possible data to view state. i.e views be rendering any of this parameters at one point or the other
 * In idle state or when no data is been processed they can be set to null.
 */
data class MainViewState(
    val allUser: List<MainEntity.User>? = null,
    val user: MainEntity.User? = null
)