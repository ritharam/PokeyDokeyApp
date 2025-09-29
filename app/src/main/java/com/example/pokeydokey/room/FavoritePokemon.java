package com.example.pokeydokey.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoritePokemon {
    @PrimaryKey
    private int id;
    private String name;
    private String imageUrl;

    public FavoritePokemon(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getImageUrl() { return imageUrl; }
}