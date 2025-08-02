package com.example.pokeydokey;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.utils.ApiClient;
import com.example.pokeydokey.utils.ApiService;
import com.squareup.picasso.Picasso;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {

    ImageView pokemonImage;
    TextView pokemonName, pokemonType, pokemonAbilities, pokemonStats;
    Button addToFavoritesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        pokemonImage = findViewById(R.id.pokemonImage);
        pokemonName = findViewById(R.id.pokemonName);
        pokemonType = findViewById(R.id.pokemonType);
        pokemonAbilities = findViewById(R.id.pokemonAbilities);
        pokemonStats = findViewById(R.id.pokemonStats);
        addToFavoritesButton = findViewById(R.id.addToFavoritesButton);

        String name = getIntent().getStringExtra("pokemon_name");

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<Pokemon> call = apiService.getPokemon(name);

        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()) {
                    Pokemon p = response.body();
                    populateUI(p);
                } else {
                    Toast.makeText(DetailsActivity.this, "Pokémon not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Toast.makeText(DetailsActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateUI(Pokemon p) {
        pokemonName.setText(capitalize(p.name));
        Picasso.get().load(p.sprites.frontDefault).into(pokemonImage);

        StringBuilder types = new StringBuilder("Type: ");
        for (Pokemon.TypeSlot t : p.types) {
            types.append(t.type.name).append(" ");
        }
        pokemonType.setText(types.toString());

        StringBuilder ab = new StringBuilder("Abilities: ");
        for (Pokemon.AbilitySlot a : p.abilities) {
            ab.append(a.ability.name).append(" ");
        }
        pokemonAbilities.setText(ab.toString());

        StringBuilder st = new StringBuilder("Stats:\n");
        for (Pokemon.StatSlot s : p.stats) {
            st.append(capitalize(s.stat.name)).append(": ").append(s.base_stat).append("\n");
        }
        pokemonStats.setText(st.toString());

        addToFavoritesButton.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("pokey_dokey_prefs", MODE_PRIVATE);
            Set<String> favorites = prefs.getStringSet("favorite_pokemon", new HashSet<>());
            Set<String> updated = new HashSet<>(favorites);
            updated.add(p.name.toLowerCase());
            prefs.edit().putStringSet("favorite_pokemon", updated).apply();
            Toast.makeText(this, p.name + " added to favorites", Toast.LENGTH_SHORT).show();
        });

    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
