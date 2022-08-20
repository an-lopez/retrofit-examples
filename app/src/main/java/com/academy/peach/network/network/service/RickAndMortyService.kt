package com.academy.peach.network.network.service

import com.academy.peach.network.model.network.response.ModelWrapper
import retrofit2.Call
import retrofit2.http.GET

interface RickAndMortyService {

    @GET("/api/character")
    fun getAllCharacters(): Call<ModelWrapper>

}
