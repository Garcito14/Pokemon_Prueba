package com.example.pokedex_prueba.data.repository

import com.example.pokedex_prueba.data.ApiService
import com.example.pokedex_prueba.data.models.PokemonDetailModel
import com.example.pokedex_prueba.data.models.PokemonSpeciesModel
import javax.inject.Inject

class PokemonDetailRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPokemonDetail(pokemonId: String): Result<PokemonDetailModel> {
        return try {
            val response = apiService.getPokemonDetail(pokemonId)
            if (response.isSuccessful) {
                response.body()?.let { Result.success(it) } ?: Result.failure(Exception("NO hay respuesta"))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun getPokemonSpecies(pokemonId: String): Result<PokemonSpeciesModel> {
        return try {
            val response = apiService.getPokemonSpecies(pokemonId)
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("No hay nada"))
            } else {
                Result.failure(Exception("Error: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}