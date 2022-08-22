package com.academy.peach.network.network.service

import com.academy.peach.network.model.network.response.ModelWrapper

class RickAndMortyClient(private val rickAndMortyService: RickAndMortyService) {

    suspend fun getCharacters(): ModelWrapper = rickAndMortyService.getCharacters()

}
