package com.academy.peach.network.network.service

import com.academy.peach.network.model.network.response.ModelWrapper
import io.reactivex.rxjava3.core.Single

class RickAndMortyClient(private val rickAndMortyService: RickAndMortyService) {

    fun getAllCharacters(): Single<ModelWrapper> = rickAndMortyService.getAllCharacters()

}
