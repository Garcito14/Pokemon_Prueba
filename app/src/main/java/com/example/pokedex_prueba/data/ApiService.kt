package com.example.pokedex_prueba.data

import com.example.pokedex_prueba.Constants.POKEMON_LIST_URL
import com.example.pokedex_prueba.data.models.PokemonModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

interface   ApiService {

        @GET(POKEMON_LIST_URL)
        suspend fun getPokemonList(
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
        ): Response<PokemonModel>


}
