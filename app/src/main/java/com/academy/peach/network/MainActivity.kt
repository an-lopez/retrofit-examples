package com.academy.peach.network

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.academy.peach.network.model.network.response.Character
import com.academy.peach.network.ui.theme.RetrofitCallTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            RetrofitCallTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(Modifier.fillMaxSize()) {
                        CharactersList(emptyList())
                    }
                }
            }
        }
    }

}

@Composable
fun CharactersList(characters: List<Character>) {
    LazyColumn {
        items(characters) { character ->
            CharacterCard(character)
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
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

