package com.example.pokedex_prueba.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pokedex_prueba.data.models.PokemonDetailModel
import com.example.pokedex_prueba.ui.viewmodels.PokemonDetailViewModel
import com.example.pokedex_prueba.ui.viewmodels.PokemonListViewModel


@Composable
fun PokemonDetailView(pokemonDetailViewModel: PokemonDetailViewModel, navController: NavController,id:String) {


    val pokemonDetail by pokemonDetailViewModel.pokemonDetail.collectAsState()
    val isLoading by pokemonDetailViewModel.isLoading.collectAsState()
    val errorMessage by pokemonDetailViewModel.errorMessage.collectAsState()
    val state by pokemonDetailViewModel.pokemonState.collectAsState()
    LaunchedEffect(id) {
        pokemonDetailViewModel.getPokemonDetail(id)
    }

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                val detail = state.pokemonDetail!!
                Text(text = detail.name.capitalize(), fontSize = 24.sp, fontWeight = FontWeight.Bold)


                val flavorText = state.speciesDetail?.flavor_text_entries
                    ?.firstOrNull { it.language.name == "es" }?.flavor_text ?: "Sin descripción"


                Image(
                    painter = rememberImagePainter(detail.sprites.front_default),
                    contentDescription = "Imagen de ${detail.name}",
                    modifier = Modifier.size(150.dp)
                )
                Text(text = flavorText, fontSize = 16.sp)
            }
        }
    }


//
//    @Composable
//    fun SpritePager(sprites: List<String?>) {
//        val pagerState = rememberPagerState()
//
//        HorizontalPager(
//            count = sprites.size,
//            state = pagerState,
//            modifier = Modifier.fillMaxWidth()
//        ) { page ->
//            Image(
//                painter = rememberImagePainter(sprites[page]),
//                contentDescription = "Sprite ${page + 1}",
//                modifier = Modifier.size(150.dp)
//            )
//        }
//    }
}