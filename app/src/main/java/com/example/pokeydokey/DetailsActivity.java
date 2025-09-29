package com.example.pokeydokey;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.pokeydokey.models.Pokemon;
import com.example.pokeydokey.room.FavoritePokemon;
import com.example.pokeydokey.ui.PokemonViewModel;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {
    private PokemonViewModel viewModel;
    private ImageView image;
    private TextView txtName, txtTypes, txtStats;
    private Button btnFav;
    private Pokemon current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        image = findViewById(R.id.details_image);
        txtName = findViewById(R.id.details_name);
        txtTypes = findViewById(R.id.details_types);
        txtStats = findViewById(R.id.details_stats);
        btnFav = findViewById(R.id.button_fav);

        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        String name = getIntent().getStringExtra("name");
        if (name == null) {
            finish();
            return;
        }

        viewModel.fetchPokemonDetails(name);

        viewModel.getSelectedPokemon().observe(this, pokemon -> {
            if (pokemon != null) {
                current = pokemon;
                displayPokemon(pokemon);
            }
        });

        viewModel.getError().observe(this, s -> {
            if (s != null) Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        });

        btnFav.setOnClickListener(v -> {
            if (current == null) return;
            FavoritePokemon fav = new FavoritePokemon(current.getId(), current.getName(),
                    current.getSprites()!=null?current.getSprites().front_default:null);
            viewModel.addFavorite(current);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        });
    }

    private void displayPokemon(Pokemon p) {
        txtName.setText(capitalize(p.getName()) + " (#" + p.getId() + ")");
        String types = "";
        if (p.getTypes() != null) {
            for (int i=0;i<p.getTypes().size();i++) {
                types += capitalize(p.getTypes().get(i).type.name);
                if (i < p.getTypes().size()-1) types += " / ";
            }
        }
        txtTypes.setText("Type: " + types);

        StringBuilder sb = new StringBuilder();
        if (p.getStats() != null) {
            for (Pokemon.StatSlot s : p.getStats()) {
                sb.append(capitalize(s.stat.name)).append(": ").append(s.base_stat).append("\n");
            }
        }
        txtStats.setText(sb.toString());

        String url = (p.getSprites()!=null)?p.getSprites().front_default:null;
        Glide.with(this).load(url).into(image);
    }
    private String capitalize(String s) {
        if (s==null||s.length()==0) return s;
        return s.substring(0,1).toUpperCase()+s.substring(1);
    }
}