package com.example.pokedex_prueba.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_favs")
data class PokemonFavEntity(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val spriteUrl: String = ""
)