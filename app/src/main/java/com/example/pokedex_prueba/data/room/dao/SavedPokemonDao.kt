package com.example.pokedex_prueba.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex_prueba.data.room.entities.SavedPokemonEntity


import kotlinx.coroutines.flow.Flow

@Dao

interface SavedPokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedPokemon(pokemon: SavedPokemonEntity)

    @Query("SELECT * FROM saved_pokemon")
    fun getSavedPokemon(): Flow<List<SavedPokemonEntity>>
}