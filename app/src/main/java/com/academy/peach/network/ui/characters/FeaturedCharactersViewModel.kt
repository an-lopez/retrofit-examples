package com.academy.peach.network.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.academy.peach.network.network.CharacterRemoteDataSource
import com.academy.peach.network.network.model.RickAndMortyCharacterNetwork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeaturedCharactersViewModel(private val characterRemoteDataSource: CharacterRemoteDataSource): ViewModel() {

    init {
        getCharacters()
    }

    private val _characters = MutableStateFlow<List<RickAndMortyCharacterNetwork>>(emptyList())
    val characters: StateFlow<List<RickAndMortyCharacterNetwork>> = _characters

    fun getCharacters(){
    }

}

class FeaturedCharactersViewModelFactory(private val characterRemoteDataSource: CharacterRemoteDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CharacterRemoteDataSource::class.java).newInstance(characterRemoteDataSource)
    }

}