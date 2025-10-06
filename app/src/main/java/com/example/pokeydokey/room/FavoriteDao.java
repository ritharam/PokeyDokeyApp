package com.example.pokeydokey.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoritePokemon favorite);

    @Delete
    void delete(FavoritePokemon favorite);

    @Query("SELECT * FROM favorites")
    LiveData<List<FavoritePokemon>> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE id = :id LIMIT 1")
    FavoritePokemon findById(int id);
}
