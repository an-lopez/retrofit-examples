package com.academy.peach.repository

import com.academy.peach.model.network.response.NetworkCharactersWrapper
import com.academy.peach.network.NetworkDataSource
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val remoteDataSource: NetworkDataSource) :
    CharacterRepository {

    override suspend fun loadCharacters(): NetworkCharactersWrapper =
        remoteDataSource.loadCharacters()

}