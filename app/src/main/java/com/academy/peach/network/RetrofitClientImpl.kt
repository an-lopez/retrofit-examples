package com.academy.peach.network

import com.academy.peach.model.network.response.NetworkCharactersWrapper
import retrofit2.create
import javax.inject.Inject

class RetrofitClientImpl @Inject constructor(private val rickAndMortyService: RickAndMortyService) :
    NetworkDataSource {

    override suspend fun loadCharacters(): NetworkCharactersWrapper = rickAndMortyService.getCharacters()

}
