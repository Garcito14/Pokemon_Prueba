package com.example.pokedex_prueba.ui.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_prueba.data.models.PokemonResults

import com.example.pokedex_prueba.data.repository.PokemonListRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonListRepository
) : ViewModel() {

    private val _pokemonList = MutableStateFlow<List<PokemonResults>>(emptyList())
    val pokemonList: StateFlow<List<PokemonResults>> get() = _pokemonList

    private val _nextPage = MutableStateFlow<String?>(null)
    val nextPage: StateFlow<String?> get() = _nextPage

    private val _previousPage = MutableStateFlow<String?>(null)
    val previousPage: StateFlow<String?> get() = _previousPage


    private val _searchResults = MutableStateFlow<List<PokemonResults>?>(null)
    val searchResults: StateFlow<List<PokemonResults>?> = _searchResults
    private var offset = 0

    init {
        getPokemonList()
    }

    fun getPokemonList() {
        viewModelScope.launch {
            repository.getPokemonList(20, offset).onSuccess {
                _pokemonList.value = it.results
                _nextPage.value = it.next
                _previousPage.value = it.previous
            }
        }
    }
    fun searchPokemon(query: String) {
        if (query.isEmpty()) {
            _searchResults.value = null
            getPokemonList()
        } else {
            viewModelScope.launch {
                repository.searchPokemon(query).onSuccess { response ->
                    _searchResults.value = response.results
                }
            }
        }
    }
    fun nextPage() {
        if (_nextPage.value != null) {
            offset += 20
            getPokemonList()
        }
    }

    fun previousPage() {
        if (_previousPage.value != null && offset >= 20) {
            offset -= 20
            getPokemonList()
        }
    }
}