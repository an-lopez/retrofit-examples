package com.academy.peach.network.network.service

import com.academy.peach.network.model.network.response.ModelWrapper
import com.academy.peach.network.util.NetworkResult
import com.academy.peach.network.util.handleApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import javax.inject.Inject

class RickAndMortyClient @Inject constructor(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(): NetworkResult<ModelWrapper> =
        handleApi { rickAndMortyService.getCharacters() }

    suspend fun getCharactersWithException(): NetworkResult<ModelWrapper> =
        handleApi { throw IllegalArgumentException("Exception thrown") }

    suspend fun getCharactersWithError(): NetworkResult<ModelWrapper> =
        handleApi {
            Response.error(401, """
            {
                "message":"Error"
            }
        """.trimIndent().toResponseBody("application/json".toMediaType()))
        }

}
