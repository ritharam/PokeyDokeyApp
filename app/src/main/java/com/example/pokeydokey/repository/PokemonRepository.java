package com.example.pokeydokey.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.pokeydokey.api.PokeApiService;
import com.example.pokeydokey.api.RetrofitClient;
import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.models.PokemonListResponse;
import com.example.pokeydokey.room.AppDatabase;
import com.example.pokeydokey.room.FavoriteDao;
import com.example.pokeydokey.room.FavoritePokemon;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;

public class PokemonRepository {
    private PokeApiService apiService;
    private FavoriteDao favoriteDao;
    private Executor dbExecutor;

    public PokemonRepository(Context context) {
        apiService = RetrofitClient.getClient().create(PokeApiService.class);
        favoriteDao = AppDatabase.getInstance(context).favoriteDao();
        dbExecutor = Executors.newSingleThreadExecutor();
    }

    public void getPokemonList(int limit, int offset, Callback<PokemonListResponse> cb) {
        apiService.getPokemonList(limit, offset).enqueue(cb);
    }

    public void getPokemon(String nameOrId, Callback<Pokemon> cb) {
        apiService.getPokemon(nameOrId.toLowerCase().trim()).enqueue(cb);
    }

    public LiveData<java.util.List<FavoritePokemon>> getAllFavorites() {
        return favoriteDao.getAllFavorites();
    }

    public void insertFavorite(final FavoritePokemon fav) {
        dbExecutor.execute(() -> favoriteDao.insert(fav));
    }

    public void deleteFavorite(final FavoritePokemon fav) {
        dbExecutor.execute(() -> favoriteDao.delete(fav));
    }

    public FavoritePokemon findFavoriteSync(final int id) {
        // NOTE: use cautiously — executes on calling thread; prefer not UI thread.
        return favoriteDao.findById(id);
    }
}
