package com.academy.peach.network.network.service

import com.academy.peach.network.model.network.response.ModelWrapper
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RickAndMortyService {

    @GET("/api/character")
    fun getAllCharacters(): Single<ModelWrapper>

}
