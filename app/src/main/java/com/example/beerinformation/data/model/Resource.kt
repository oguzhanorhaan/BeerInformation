package com.example.beerinformation.data.model

data class Resource<out T>(val status: BeersApiStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(BeersApiStatus.DONE, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(BeersApiStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(BeersApiStatus.LOADING, data, null)
        }
    }
}