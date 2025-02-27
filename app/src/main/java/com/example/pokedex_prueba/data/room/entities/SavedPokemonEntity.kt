package com.example.pokedex_prueba.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_pokemon")
data class SavedPokemonEntity(
    @PrimaryKey val id: String,
    val name: String = "",
    val imageUrl: String = ""

)


