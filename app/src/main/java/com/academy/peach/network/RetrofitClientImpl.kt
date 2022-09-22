package com.academy.peach.network

import com.academy.peach.model.network.response.NetworkCharactersWrapper
import retrofit2.create

class RetrofitClientImpl(private val rickAndMortyService: RickAndMortyService = RetrofitSingleton.retrofit.create()) :
    NetworkDataSource {
    override suspend fun loadCharacters(): NetworkCharactersWrapper = rickAndMortyService.getCharacters()
}