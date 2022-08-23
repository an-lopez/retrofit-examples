package com.academy.peach.network.util

import retrofit2.HttpException
import retrofit2.Response

sealed interface NetworkResult<T: Any>

data class ApiSuccess<T: Any>(val data: T) : NetworkResult<T>
data class ApiError<T: Any>(val code: Int, val message: String) : NetworkResult<T>
data class ApiException<T: Any>(val e: Throwable) : NetworkResult<T>

suspend fun <T: Any> handleApi(
    execute: suspend () -> Response<T>
): NetworkResult<T>{
    return try{
        val response = execute()
        val body = response.body()
        if(response.isSuccessful && body != null){
            ApiSuccess(body)
        }else{
            ApiError(response.code(), response.message())
        }
    }catch (e: HttpException){
        ApiError(e.code(), e.message())
    }catch (e: Throwable){
        ApiException(e)
    }
}

suspend fun <T: Any> NetworkResult<T>.onSuccess(
    executable: suspend (T) -> Unit
): NetworkResult<T> = apply {
    if(this is ApiSuccess<T>){
        executable(data)
    }
}

suspend fun <T: Any> NetworkResult<T>.onError(
    executable: suspend (code: Int, message: String) -> Unit
): NetworkResult<T> = apply {
    if(this is ApiError<T>){
        executable(code, message)
    }
}

suspend fun <T: Any> NetworkResult<T>.onException(
    executable: suspend (Throwable) -> Unit
): NetworkResult<T> = apply {
    if(this is ApiException<T>){
        executable(e)
    }
}