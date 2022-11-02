package com.academy.peach.network

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.Room
import androidx.room.RoomDatabase
import coil.compose.AsyncImage
import com.academy.peach.network.database.RickAndMortyDatabase
import com.academy.peach.network.domain.CharacterView
import com.academy.peach.network.network.service.RickAndMortyClient
import com.academy.peach.network.model.network.response.Character
import com.academy.peach.network.network.RetrofitClient
import com.academy.peach.network.network.service.RickAndMortyService
import com.academy.peach.network.ui.characters.CharacterUiState
import com.academy.peach.network.ui.characters.FeaturedCharactersViewModel
import com.academy.peach.network.ui.characters.FeaturedCharactersViewModelFactory
import com.academy.peach.network.ui.theme.RetrofitCallTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: FeaturedCharactersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val state: CharacterUiState by viewModel.characters.collectAsState()

            RetrofitCallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(Modifier.fillMaxSize()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(
                                4.dp,
                                Alignment.CenterHorizontally
                            )
                        ) {
                            Button(onClick = viewModel::getCharacters) {
                                Text(text = "Characters")
                            }

                        }

                        CharactersList(state.characters)

                        state.errorMessage?.let {
                            Text(text = it)
                        }

                        state.exception?.let {
                            Text(text = it.message ?: "Empty exception")
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CharactersList(characters: List<CharacterView>) {
    LazyColumn {
        items(characters) { character ->
            CharacterCard(character)
        }
    }
}

@Composable
fun CharacterCard(character: CharacterView) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = character.url, contentDescription = character.name,
            modifier = Modifier
                .height(48.dp)
                .width(48.dp)
        )
        Text(text = character.name)
    }
}

