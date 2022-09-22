package com.academy.peach

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.academy.peach.interactor.LoadCharactersUseCase
import com.academy.peach.model.network.response.Info
import com.academy.peach.model.network.response.NetworkCharactersWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel(private val loadCharactersUseCase: LoadCharactersUseCase) : ViewModel() {


    //backed properties
    private val _characters = MutableStateFlow(
        emptyList<com.academy.peach.model.view.Character>())

    val characters: StateFlow<List<com.academy.peach.model.view.Character>> =
        _characters.asStateFlow()

    fun loadCharacters() {
        viewModelScope.launch {
            val result = loadCharactersUseCase()
            _characters.update {
                result
            }
        }
    }

}

class CharactersViewModelFactory(
    private val loadCharactersUseCase: LoadCharactersUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CharactersViewModel(loadCharactersUseCase) as T
    }

}