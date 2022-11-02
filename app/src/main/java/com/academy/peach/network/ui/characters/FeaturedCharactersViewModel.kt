package com.academy.peach.network.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.academy.peach.network.database.dao.CharacterDao
import com.academy.peach.network.database.model.CharacterEntity
import com.academy.peach.network.domain.CharacterView
import com.academy.peach.network.network.service.RickAndMortyClient
import com.academy.peach.network.model.network.response.Character
import com.academy.peach.network.model.network.response.ModelWrapper
import com.academy.peach.network.util.ApiError
import com.academy.peach.network.util.ApiException
import com.academy.peach.network.util.ApiSuccess
import com.academy.peach.network.util.NetworkResult
import com.academy.peach.network.util.onError
import com.academy.peach.network.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

interface CharacterRepository{

    suspend fun getCharacters(): NetworkResult<ModelWrapper>

    suspend fun getCharactersFlow(): Flow<List<CharacterEntity>>

    suspend fun insertCharacter(entities: List<CharacterEntity>)

}

class CharacterRepositoryImpl @Inject constructor(
    private val rickAndMortyClient: RickAndMortyClient,
    private val characterDao: CharacterDao,
): CharacterRepository{
    override suspend fun getCharacters(): NetworkResult<ModelWrapper> = rickAndMortyClient.getCharacters()

    override suspend fun getCharactersFlow(): Flow<List<CharacterEntity>> = characterDao.getCharacters()

    override suspend fun insertCharacter(entities: List<CharacterEntity>) = characterDao.insertCharacter(entities)

}

class GetCharacterUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
){

    suspend operator fun invoke(): NetworkResult<ModelWrapper> = characterRepository.getCharacters()

}

@HiltViewModel
class FeaturedCharactersViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val getCharacterUseCase: GetCharacterUseCase,
) :
    ViewModel() {

    private val _characters = MutableStateFlow(CharacterUiState())
    val characters: StateFlow<CharacterUiState> = _characters

    fun getCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            getCharacterUseCase().onSuccess {
                val entities = it.results.map { character ->
                    CharacterEntity(
                        name = character.name,
                        image = character.image,
                    )
                }
                characterRepository.insertCharacter(entities = entities)
                val result = characterRepository.getCharactersFlow().first().map { character ->
                    CharacterView(
                        name = character.name,
                        url = character.image
                    )
                }
                _characters.value = CharacterUiState(characters = result)
            }.onError { code, message ->

            }

        }
    }


}

data class CharacterUiState(
    val characters: List<CharacterView> = emptyList(),
    val errorMessage: String? = null,
    val exception: Throwable? = null,
)

class FeaturedCharactersViewModelFactory(
    private val rickAndMortyClient: RickAndMortyClient,
    private val dao: CharacterDao,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            RickAndMortyClient::class.java,
            CharacterDao::class.java
        )
            .newInstance(rickAndMortyClient, dao)
    }

}