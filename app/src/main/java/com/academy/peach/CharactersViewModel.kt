package com.academy.peach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.academy.peach.interactor.LoadCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val loadCharactersUseCase: LoadCharactersUseCase,
) : ViewModel() {

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