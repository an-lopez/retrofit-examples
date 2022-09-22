package com.academy.peach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.academy.peach.interactor.LoadCharactersUseCase
import com.academy.peach.model.network.response.NetworkCharacter
import com.academy.peach.network.RetrofitClientImpl
import com.academy.peach.repository.CharacterRepositoryImpl
import com.academy.peach.ui.theme.RetrofitCallTheme

class MainActivity : ComponentActivity() {

    private val viewModel: CharactersViewModel by viewModels{
        CharactersViewModelFactory(
            LoadCharactersUseCase(
                CharacterRepositoryImpl(
                    RetrofitClientImpl()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {

            val characters by viewModel.characters.collectAsState()

            RetrofitCallTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(Modifier.fillMaxSize()) {
                        //Button(onClick = viewModel::loadCharacters)
                        Button(onClick = {
                            viewModel.loadCharacters()
                        }){
                            Text("Carga los datos")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        CharactersList(characters)
                    }
                }
            }
        }
    }

}

@Composable
fun CharactersList(characters: List<com.academy.peach.model.view.Character>) {
    LazyColumn {
        items(characters) { character ->
            CharacterCard(character)
        }
    }
}

@Composable
fun CharacterCard(character: com.academy.peach.model.view.Character) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = character.image, contentDescription = character.name,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
        )
        Text(text = character.name)
    }
}

