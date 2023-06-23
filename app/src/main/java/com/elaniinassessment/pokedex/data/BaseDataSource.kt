package com.elaniinassessment.pokedex.data

import retrofit2.Response
import java.io.Serializable

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>?): Resource<T> {
        val response = call()
        return if (response?.isSuccessful == true) {
            val body = response.body()
            Resource.success(body)
        } else Resource.error("Error")

    }
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) : Serializable {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                message
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

}