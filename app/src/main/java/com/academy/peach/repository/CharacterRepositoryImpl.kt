package com.academy.peach.repository

import com.academy.peach.model.network.response.NetworkCharactersWrapper
import com.academy.peach.network.NetworkDataSource

class CharacterRepositoryImpl(private val remoteDataSource: NetworkDataSource) : CharacterRepository{

    override suspend fun loadCharacters(): NetworkCharactersWrapper =
        remoteDataSource.loadCharacters()

}