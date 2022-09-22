package com.academy.peach.interactor

import com.academy.peach.model.network.response.NetworkCharacter
import com.academy.peach.model.network.response.asExternalModel
import com.academy.peach.repository.CharacterRepository

class LoadCharactersUseCase(private val repository: CharacterRepository) {

    suspend operator fun invoke(): List<com.academy.peach.model.view.Character> =
        repository.loadCharacters().characters.map(NetworkCharacter::asExternalModel)

}