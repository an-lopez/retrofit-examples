package com.academy.peach.network.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.academy.peach.network.network.service.RickAndMortyClient
import com.academy.peach.network.model.network.response.Character
import com.academy.peach.network.util.ApiError
import com.academy.peach.network.util.ApiException
import com.academy.peach.network.util.ApiSuccess
import com.academy.peach.network.util.onError
import com.academy.peach.network.util.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeaturedCharactersViewModel(private val rickAndMortyClient: RickAndMortyClient) :
    ViewModel() {

    private val _characters = MutableStateFlow(CharacterUiState())
    val characters: StateFlow<CharacterUiState> = _characters

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            rickAndMortyClient.getCharacters().onSuccess {

            }.onError{ code, message ->

            }
            _characters.value = when (val result = rickAndMortyClient.getCharacters()) {
                is ApiSuccess -> CharacterUiState(characters = result.data.results)
                is ApiException -> CharacterUiState(exception = result.e)
                is ApiError -> CharacterUiState(errorMessage = result.message)
            }
        }
    }

    fun getException() {
        viewModelScope.launch(Dispatchers.IO) {
            _characters.value =
                when (val result = rickAndMortyClient.getCharactersWithException()) {
                    is ApiSuccess -> CharacterUiState(characters = result.data.results)
                    is ApiException -> CharacterUiState(exception = result.e)
                    is ApiError -> CharacterUiState(errorMessage = "${result.code} ${result.message}")
                }
        }
    }

    fun getError() {
        viewModelScope.launch(Dispatchers.IO) {
            _characters.value = when (val result = rickAndMortyClient.getCharactersWithError()) {
                is ApiSuccess -> CharacterUiState(characters = result.data.results)
                is ApiException -> CharacterUiState(exception = result.e)
                is ApiError -> CharacterUiState(errorMessage = "${result.code} ${result.message}")
            }
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