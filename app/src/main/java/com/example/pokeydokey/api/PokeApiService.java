package com.example.pokeydokey.api;

import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.models.PokemonListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokeApiService {
    @GET("pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("pokemon/{name}")
    Call<Pokemon> getPokemon(@Path("name") String name);
}
