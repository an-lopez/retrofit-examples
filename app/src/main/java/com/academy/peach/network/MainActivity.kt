package com.academy.peach.network

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.academy.peach.network.network.CharacterRemoteDataSource
import com.academy.peach.network.network.model.RickAndMortyCharacterNetwork
import com.academy.peach.network.ui.characters.FeaturedCharactersViewModel
import com.academy.peach.network.ui.characters.FeaturedCharactersViewModelFactory
import com.academy.peach.network.ui.theme.RetrofitCallTheme

class MainActivity : ComponentActivity() {

    private val viewModel: FeaturedCharactersViewModel by viewModels {
        FeaturedCharactersViewModelFactory(CharacterRemoteDataSource())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val state: List<RickAndMortyCharacterNetwork> by viewModel.characters.collectAsState(emptyList())

            RetrofitCallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CharactersList(state)
                }
            }
        }
    }

}

@Composable
fun CharactersList(characters: List<RickAndMortyCharacterNetwork>) {

    LazyColumn{
        items(characters){ character ->
            CharacterCard(character)
        }
    }
}

@Composable
fun CharacterCard(character: RickAndMortyCharacterNetwork) {
    Column(modifier = Modifier.fillMaxWidth()) {
        AsyncImage(
            model = character.image, contentDescription = character.name,
        modifier = Modifier.height(48.dp).width(48.dp))
        Text(text = character.name)
    }
}

