package com.example.pokedex_prueba.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex_prueba.data.room.dao.PokemonRoomDao
import com.example.pokedex_prueba.data.room.dao.SavedPokemonDao

import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import com.example.pokedex_prueba.data.room.entities.SavedPokemonEntity


@Database(
    entities = [PokemonFavEntity::class, SavedPokemonEntity::class],
    version = 1,
    exportSchema = false
)

abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonRoomDao
    abstract fun savedPokemonDao(): SavedPokemonDao

}
