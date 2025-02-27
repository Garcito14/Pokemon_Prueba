package com.example.pokedex_prueba.data.state

import com.example.pokedex_prueba.data.models.PokemonDetailModel
import com.example.pokedex_prueba.data.models.PokemonSpeciesModel

data class PokemonDetailState(
    val isLoading: Boolean = true,
    val pokemonDetail: PokemonDetailModel? = null,
    val speciesDetail: PokemonSpeciesModel? = null,
    val errorMessage: String? = null
)
