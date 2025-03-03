package com.example.pokedex_prueba.ui.navigation



import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex_prueba.ui.viewmodels.FavoritePokemonViewModel
import com.example.pokedex_prueba.ui.viewmodels.PokemonDetailViewModel

import com.example.pokedex_prueba.ui.viewmodels.PokemonListViewModel
import com.example.pokedex_prueba.ui.views.PokemonDetailView
import com.example.pokedex_prueba.ui.views.PokemonFavoritesScreen
import com.example.pokedex_prueba.ui.views.PokemonListScreen
import com.example.pokedex_prueba.ui.views.SavedPokemonView


@Composable
fun NavManager(
    navController: NavController,
    pokemonViewModel: PokemonListViewModel,
    pokemonDetailViewModel: PokemonDetailViewModel,
    favoritePokemonViewModel: FavoritePokemonViewModel



    ) {

    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = "pokemon_home") {


        composable("pokemon_home") {
            PokemonListScreen(pokemonViewModel,navController)
        }




        composable(
            route = "pokemon_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            id?.let {
                PokemonDetailView(
                    navController = navController,
                    pokemonDetailViewModel = pokemonDetailViewModel,
                    id = it
                )
            }
        }

        composable("pokemon_favorites") {
            PokemonFavoritesScreen(pokemonViewModel,favoritePokemonViewModel, navController)
        }

        composable("saved_pokemon") {
            SavedPokemonView(pokemonViewModel,navController)
        }
    }
}
