package com.example.pokedex_prueba.ui.views

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokedex_prueba.data.models.PokemonDetailModel
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import com.example.pokedex_prueba.data.state.PokemonFavoriteState
import com.example.pokedex_prueba.ui.viewmodels.PokemonDetailViewModel
import com.example.pokedex_prueba.ui.viewmodels.PokemonListViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable

fun PokemonDetailView(pokemonDetailViewModel: PokemonDetailViewModel, navController: NavController, id: String) {
    val state by pokemonDetailViewModel.pokemonState.collectAsState()
    val isFavorite by pokemonDetailViewModel.isFavorite.collectAsState()
    val flavorText = state.speciesDetail?.flavor_text_entries
        ?.firstOrNull { it.language.name == "es" }?.flavor_text ?: "Sin descripción"
        val context = LocalContext.current
    LaunchedEffect(id) {
        pokemonDetailViewModel.getPokemonDetail(id)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.errorMessage ?: "Error desconocido", fontSize = 18.sp)
                }
            }

            state.pokemonDetail != null -> {
                val detail = state.pokemonDetail!!
                val spriteUrls = listOfNotNull(
                    detail.sprites.front_default,
                    detail.sprites.back_default,
                    detail.sprites.front_shiny,
                    detail.sprites.back_shiny
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = detail.name.capitalize(),
                        style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold)
                    )

                    if (spriteUrls.isNotEmpty()) {
                        SpriteCarrusel(sprites = spriteUrls)
                    } else {
                        Text(text = "No hay sprites disponibles", fontSize = 16.sp)
                    }

                    Spacer(Modifier.height(10.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = flavorText,
                            style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center),
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Row {

                        IconButton(
                            onClick = {
                                pokemonDetailViewModel.toggleFavorite(PokemonFavoriteState(pokemonId = id))
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Favorito",
                                tint = if (isFavorite) Color.Red else Color.Gray,
                                modifier = Modifier.size(35.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))


                        IconButton(
                            onClick = {
                                sharePokemon(context, detail.sprites.front_default!!)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartir",
                                tint = Color.Blue,
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                }
            }
        }
    }


}


private fun sharePokemon(context: Context, pokemonName: String) {
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, "¡Mira este Pokémon: $pokemonName")
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Compartir Pokémon")
    context.startActivity(shareIntent)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SpriteCarrusel(sprites: List<String>) {
    val pagerState = rememberPagerState(pageCount = { 1000 })

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(

            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Altura del carrusel
        ) { page ->
            val index = page % sprites.size
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberImagePainter(sprites[index]),
                    contentDescription = "Sprite ${index + 1}",
                    modifier = Modifier.size(150.dp)

                )
            }
        }


        Text(
            text = "Sprite ${pagerState.currentPage  % sprites.size +1 } de ${sprites.size}",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

