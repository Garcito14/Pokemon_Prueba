package com.example.pokedex_prueba.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_prueba.data.models.PokemonResults
import com.example.pokedex_prueba.data.repository.PokemonDetailRepository
import com.example.pokedex_prueba.data.repository.PokemonFavRepository
import com.example.pokedex_prueba.data.room.entities.PokemonFavEntity
import com.example.pokedex_prueba.data.state.PokemonFavoriteState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritePokemonViewModel @Inject constructor(
    private val repository: PokemonDetailRepository,
    private val pokemonfavRepository: PokemonFavRepository
) : ViewModel() {

    private val _favoritePokemonList = MutableStateFlow<List<PokemonFavEntity>>(emptyList())
    val favoritePokemonList: StateFlow<List<PokemonFavEntity>> = _favoritePokemonList

    init {
        loadFavoritePokemon()
    }

    fun loadFavoritePokemon() {
        viewModelScope.launch {
            pokemonfavRepository.getAllFavorites().collect { favoriteList ->
                _favoritePokemonList.value = favoriteList
            }
        }
    }




}
