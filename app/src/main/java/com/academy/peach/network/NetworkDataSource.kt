package com.academy.peach.network

import com.academy.peach.model.network.response.NetworkCharactersWrapper

interface NetworkDataSource {

    suspend fun loadCharacters(): NetworkCharactersWrapper

}