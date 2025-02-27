package com.example.pokedex_prueba.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex_prueba.data.models.PokemonResults
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import com.example.pokedex_prueba.data.state.PokemonFavoriteState
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonRoomDao {

    @Query("SELECT * FROM pokemon_favs")
    fun getAllPokemonFav(): Flow<List<PokemonFavEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM pokemon_favs WHERE pokemonId = :pokemonId)")
    fun isFavorite(pokemonId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFav(pokemon: PokemonFavEntity)

    @Query("DELETE FROM pokemon_favs WHERE pokemonId = :pokemonId")
    suspend fun removeFav(pokemonId: String)
}
