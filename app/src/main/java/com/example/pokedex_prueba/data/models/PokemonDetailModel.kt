package com.example.pokedex_prueba.data.models

data class PokemonDetailModel(
    val name: String,
    val sprites: Sprites,
    val pokemonSpeciesModel: PokemonSpeciesModel? = null
)


data class Sprites(
    val front_default: String?,
    val back_default: String?,
    val front_shiny: String?,
    val back_shiny: String?
)

data class PokemonSpeciesModel(
    val flavor_text_entries: List<FlavorTextEntry>
)

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language
)

data class Language(
    val name: String
)