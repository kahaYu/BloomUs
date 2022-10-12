package com.yurikolesnikov.network

import com.yurikolesnikov.uicomponents.UiText

sealed class Resource<T>(val data: T? = null, val message: UiText? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null, message: UiText? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()


}
