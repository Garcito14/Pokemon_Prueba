package com.example.pokedex_prueba.data


import com.example.pokedex_prueba.Constants.URL
import com.example.pokedex_prueba.data.models.PokemonDetailModel
import com.example.pokedex_prueba.data.models.PokemonModel
import com.example.pokedex_prueba.data.models.PokemonSpeciesModel
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
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface   ApiService {

        @GET(URL)
        suspend fun getPokemonList(
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
        ): Response<PokemonModel>




        @GET("pokemon/{pokemonId}")
        suspend fun getPokemonDetail(
            @Path("pokemonId") pokemonId: String
        ): Response<PokemonDetailModel>



    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: String): Response<PokemonSpeciesModel>
}


