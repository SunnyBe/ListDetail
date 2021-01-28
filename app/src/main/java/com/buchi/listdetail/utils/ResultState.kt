package com.buchi.listdetail.utils

data class ResultState<T>(
        var loading: Boolean = false,
        var data: Event<T>? = null,
        val error: Throwable?= null,
        val message: String?=null
) {
    companion object {

        fun <T> error(
                error: Throwable
        ): ResultState<T> {
            return ResultState(
                    loading = false,
                    data = null,
                    error = error
            )
        }

        fun <T> error(
                message: String
        ): ResultState<T> {
            return ResultState(
                    loading = false,
                    data = null,
                    message = message
            )
        }

        fun <T> loading(
                isLoading: Boolean,
                loadingMsg: String?=null
        ): ResultState<T> {
            return ResultState(
                    loading = isLoading,
                    message = loadingMsg,
                    data = null,
                    error = null
            )
        }

        fun <T> data(
                data: T? = null
        ): ResultState<T> {
            return ResultState(
                    loading = false,
                    data = Event.dataEvent(data),
                    error = null
            )
        }
    }

    override fun toString(): String {
        return "com.ajocard.core.utils.DataState(loading=$loading,data=$data)"
    }
}