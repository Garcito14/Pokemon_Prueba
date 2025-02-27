package com.example.pokedex_prueba.ui.navigation



import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.example.pokedex_prueba.ui.viewmodels.PokemonListViewModel
import com.example.pokedex_prueba.ui.views.PokemonListScreen


@Composable
fun NavManager(
    navController: NavController,
    pokemonViewModel: PokemonListViewModel,



    ) {

    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "pokemon_home") {


        composable("pokemon_home") {
            PokemonListScreen(pokemonViewModel,navController)
        }

        composable("pokemon_detail") {
            PokemonListScreen(pokemonViewModel,navController)
        }



    }
}
