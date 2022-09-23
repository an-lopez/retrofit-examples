package com.academy.peach.interactor

import com.academy.peach.model.network.response.NetworkCharacter
import com.academy.peach.model.network.response.NetworkCharactersWrapper
import com.academy.peach.model.network.response.asExternalModel
import com.academy.peach.network.NetworkDataSource
import com.academy.peach.repository.CharacterRepository
import javax.inject.Inject

class LoadCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {

    val interfaz = object: NetworkDataSource{
        override suspend fun loadCharacters(): NetworkCharactersWrapper {
            TODO("Not yet implemented")
        }

    }

    val interfaz2 = object: NetworkDataSource{
        override suspend fun loadCharacters(): NetworkCharactersWrapper {
            TODO("Not yet implemented")
        }

    }

    suspend operator fun invoke(): List<com.academy.peach.model.view.Character> =
        repository.loadCharacters().characters.map(NetworkCharacter::asExternalModel)

}