package com.example.pokeydokey.utils;

import com.example.pokeydokey.models.Pokemon;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("pokemon/{name}")
    Call<Pokemon> getPokemon(@Path("name") String name);
}
