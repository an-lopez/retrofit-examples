package com.academy.peach.network.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.academy.peach.network.network.service.RickAndMortyClient
import com.academy.peach.network.model.network.response.Character
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeaturedCharactersViewModel(private val rickAndMortyClient: RickAndMortyClient) :
    ViewModel() {

    val characters: StateFlow<List<Character>> = _characters
    private val _characters = MutableStateFlow(CharacterUiState())
    val characters: StateFlow<CharacterUiState> = _characters

    init {
        getCharacters()
    }

    private fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            _characters.value = rickAndMortyClient.getCharacters().results
        }
    }

}

data class CharacterUiState(
    val characters: List<Character> = emptyList(),
    val errorMessage: String? = null,
    val exception: Throwable? = null
)

class FeaturedCharactersViewModelFactory(private val rickAndMortyClient: RickAndMortyClient) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RickAndMortyClient::class.java)
            .newInstance(rickAndMortyClient)
    }

}