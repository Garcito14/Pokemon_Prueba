package com.example.pokedex_prueba.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokedex_prueba.R
import com.example.pokedex_prueba.data.models.PokemonResults
import com.example.pokedex_prueba.ui.viewmodels.FavoritePokemonViewModel
import com.example.pokedex_prueba.ui.viewmodels.PokemonDetailViewModel
import com.example.pokedex_prueba.ui.viewmodels.PokemonListViewModel
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun PokemonListScreen(pokemonViewModel: PokemonListViewModel, navController: NavController) {
    val pokemonList by pokemonViewModel.pokemonList.collectAsState()
    val searchResults by pokemonViewModel.searchResults.collectAsState()
    val nextPage by pokemonViewModel.nextPage.collectAsState()
    val previousPage by pokemonViewModel.previousPage.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var isSearchVisible by remember { mutableStateOf(false) } // 🔹 Controla la visibilidad del SearchBar

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.pokedex_fondo),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .padding(vertical = 50.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pokédex",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color.White
                    )
                }
            }


            if (isSearchVisible) {
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        pokemonViewModel.searchPokemon(it)
                    },
                    label = { Text("Buscar Pokemon", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp)),
                    singleLine = true,
                    textStyle = TextStyle(color = Color.Black),

                    trailingIcon = {
                        IconButton(

                            onClick = { isSearchVisible = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Cerrar búsqueda",
                                tint = Color.White,

                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            val displayList = if (searchQuery.isEmpty()) pokemonList else searchResults ?: emptyList()

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f)
            ) {
                items(displayList) { pokemon ->
                    PokemonItem(pokemon, navController)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (searchQuery.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { pokemonViewModel.previousPage() },
                        enabled = previousPage != null
                    ) {
                        Text("Pag - ")
                    }

                    Button(
                        onClick = { navController.navigate("pokemon_favorites") },
                    ) {
                        Text("Pokemon Favoritos")
                    }

                    Button(
                        onClick = { pokemonViewModel.nextPage() },
                        enabled = nextPage != null
                    ) {
                        Text("Pag +")
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: PokemonResults, navController: NavController) {
    val pokemonId = pokemon.url.split("/").dropLast(1).last()
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { navController.navigate("pokemon_detail/$pokemonId") },
        colors = CardDefaults.cardColors(
            containerColor = Color.Black.copy(alpha = 0.5f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)

    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .size(100.dp)
            )
            Text(
                text = pokemon.name.capitalize(),
                style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
