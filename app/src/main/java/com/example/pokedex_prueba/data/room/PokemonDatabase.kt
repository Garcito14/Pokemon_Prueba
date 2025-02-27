package com.example.pokedex_prueba.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex_prueba.data.room.dao.PokemonRoomDao
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity

@Database(
    entities = [PokemonFavEntity::class],
    version = 1,
    exportSchema = false
)

abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonRoomDao

}
