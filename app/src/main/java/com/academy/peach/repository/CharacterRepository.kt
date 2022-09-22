package com.academy.peach.repository

import com.academy.peach.model.network.response.NetworkCharactersWrapper

interface CharacterRepository {

    suspend fun loadCharacters(): NetworkCharactersWrapper

}
