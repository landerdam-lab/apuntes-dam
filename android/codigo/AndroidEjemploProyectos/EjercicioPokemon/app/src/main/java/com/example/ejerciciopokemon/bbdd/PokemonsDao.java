package com.example.ejerciciopokemon.bbdd;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ejerciciopokemon.model.Pokemon;

import java.util.List;

@Dao
public interface PokemonsDao
{
    @Query("SELECT * FROM Pokemon ORDER BY id")
    LiveData<List<Pokemon>> loadAllPokemons();

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Update
    void updatePokemon(Pokemon pokemon);

    @Delete
    void delete(Pokemon pokemon);

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    Pokemon loadPokemonById(int id);

    @Query("SELECT * FROM Pokemon WHERE tipo = :tipo")
    LiveData<List<Pokemon>> loadPokemonByType(String tipo);

}
