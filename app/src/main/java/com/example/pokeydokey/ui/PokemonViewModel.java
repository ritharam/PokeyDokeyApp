package com.example.pokeydokey.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.models.PokemonListResponse;
import com.example.pokeydokey.repository.PokemonRepository;
import com.example.pokeydokey.room.FavoritePokemon;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class PokemonViewModel extends AndroidViewModel {
    private PokemonRepository repository;
    private MutableLiveData<List<com.example.pokeydokey.models.PokemonResult>> pokemonList = new MutableLiveData<>();
    private MutableLiveData<Pokemon> selectedPokemon = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
    }

    public LiveData<List<com.example.pokeydokey.models.PokemonResult>> getPokemonList() { return pokemonList; }
    public LiveData<Pokemon> getSelectedPokemon() { return selectedPokemon; }
    public LiveData<String> getError() { return error; }

    public void fetchPokemonList(int limit, int offset) {
        repository.getPokemonList(limit, offset, new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    pokemonList.postValue(response.body().getResults());
                } else {
                    error.postValue("Failed to fetch list");
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

    public void fetchPokemonDetails(String nameOrId) {
        repository.getPokemon(nameOrId, new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful() && response.body() != null) {
                    selectedPokemon.postValue(response.body());
                } else {
                    error.postValue("Pokemon not found");
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                error.postValue(t.getMessage());
            }
        });
    }

    public void addFavorite(Pokemon p) {
        if (p == null) return;
        String img = (p.getSprites() != null) ? p.getSprites().front_default : null;
        FavoritePokemon fav = new FavoritePokemon(p.getId(), p.getName(), img);
        repository.insertFavorite(fav);
    }

    public void removeFavorite(FavoritePokemon fav) {
        repository.deleteFavorite(fav);
    }

    public LiveData<java.util.List<com.example.pokeydokey.room.FavoritePokemon>> getAllFavorites() {
        return repository.getAllFavorites();
    }
}