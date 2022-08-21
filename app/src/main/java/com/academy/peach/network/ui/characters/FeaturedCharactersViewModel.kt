package com.academy.peach.network.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.academy.peach.network.network.service.RickAndMortyClient
import com.academy.peach.network.model.network.response.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeaturedCharactersViewModel(private val characterRemoteDataSource: RickAndMortyClient): ViewModel() {

    init {
        getCharacters()
    }

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    fun getCharacters(){
    }

}

class FeaturedCharactersViewModelFactory(private val characterRemoteDataSource: RickAndMortyClient): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RickAndMortyClient::class.java).newInstance(characterRemoteDataSource)
    }

}