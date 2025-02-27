package com.example.pokedex_prueba.data.repository

import com.example.pokedex_prueba.data.room.dao.PokemonRoomDao
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonFavRespository @Inject constructor(private val pokemonDao: PokemonRoomDao) {

    fun getAllFavorites(): Flow<List<PokemonFavEntity>> {
        return pokemonDao.getAllPokemonFav()
    }

    suspend fun addToFavorites(pokemon: PokemonFavEntity) {
        pokemonDao.addPokemonFav(pokemon)
    }

    suspend fun removeFromFavorites(pokemon: PokemonFavEntity) {
        pokemonDao.removePokemonFav(pokemon)
    }

    suspend fun isPokemonFavorite(id: String): Boolean {
        return pokemonDao.isPokemonFav(id)
    }
}
