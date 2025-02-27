package com.example.pokedex_prueba.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex_prueba.data.models.PokemonDetailModel
import com.example.pokedex_prueba.data.models.PokemonResults
import com.example.pokedex_prueba.data.repository.PokemonDetailRepository
import com.example.pokedex_prueba.data.repository.PokemonListRepository
import com.example.pokedex_prueba.data.state.PokemonDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonDetailRepository
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow<PokemonDetailModel?>(null)
    val pokemonDetail: StateFlow<PokemonDetailModel?> = _pokemonDetail

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _pokemonState = MutableStateFlow(PokemonDetailState())
    val pokemonState: StateFlow<PokemonDetailState> = _pokemonState

    fun getPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            _pokemonState.value = PokemonDetailState(isLoading = true)

            val detailResult = repository.getPokemonDetail(pokemonId)
            val speciesResult = repository.getPokemonSpecies(pokemonId)

            if (detailResult.isSuccess && speciesResult.isSuccess) {
                _pokemonState.value = PokemonDetailState(
                    isLoading = false,
                    pokemonDetail = detailResult.getOrNull(),
                    speciesDetail = speciesResult.getOrNull()
                )
            } else {
                _pokemonState.value = PokemonDetailState(
                    isLoading = false,
                    errorMessage = "Error al cargar detalles"
                )
            }
        }
    }
}
