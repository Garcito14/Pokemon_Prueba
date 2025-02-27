package com.example.pokedex_prueba.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonRoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemonFav(pokemon: PokemonFavEntity)

    @Delete
    suspend fun removePokemonFav(pokemon: PokemonFavEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    suspend fun isPokemonFav(id: String): Boolean

    @Query("SELECT * FROM favorites")
    fun getAllPokemonFav(): Flow<List<PokemonFavEntity>>
}