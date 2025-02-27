package com.example.pokedex_prueba.data.repository

import com.example.pokedex_prueba.data.models.PokemonResults
import com.example.pokedex_prueba.data.room.dao.PokemonRoomDao
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import com.example.pokedex_prueba.data.state.PokemonFavoriteState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonFavRepository @Inject constructor(private val pokemonFavDao: PokemonRoomDao) {

    fun isPokemonFavorite(pokemonId: String): Flow<Boolean> =
        pokemonFavDao.isFavorite(pokemonId)

    fun getAllFavorites(): Flow<List<PokemonFavEntity>> =
        pokemonFavDao.getAllPokemonFav()

    suspend fun addToFavorites(pokemon: PokemonFavEntity) {
        pokemonFavDao.addFav(pokemon)
    }

    suspend fun removeFromFavorites(pokemonId: String) {
        pokemonFavDao.removeFav(pokemonId)
    }


}