package com.academy.peach.network

import com.academy.peach.model.network.response.NetworkCharactersWrapper
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyService {

    @GET("/api/character")
    suspend fun getCharacters(): NetworkCharactersWrapper

}