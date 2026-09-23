package com.example.ejerciciodialogopokemon.bbdd;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ejerciciodialogopokemon.model.Pokemon;

import java.util.List;

// DAO = Data Access Object (ver conceptos/17-room-base-de-datos.md): interfaz donde solo se
// escribe la cabecera de cada consulta, Room genera el codigo real por debajo.
@Dao
public interface PokemonDao {

    // Todos los Pokemon. LiveData = "dato observable": si alguien lo observa, se entera solo
    // cada vez que la tabla cambia (no se usa en este proyecto, se prefiere filtrar por tipo).
    @Query("SELECT * FROM Pokemon ORDER BY id")
    LiveData<List<Pokemon>> loadAllPokemon();

    @Insert
    void insertPokemon(Pokemon pokemon);

    @Update
    void updatePokemon(Pokemon pokemon);

    @Delete
    void deletePokemon(Pokemon pokemon);

    // Busca uno por id. @Delete y @Update necesitan el objeto completo, por eso se usa para
    // recuperarlo antes de borrar/actualizar (ver MainActivity.eliminar y UpdateActivity).
    @Query("SELECT * FROM Pokemon WHERE id = :id")
    Pokemon loadPokemonById(int id);

    // Filtra la tabla por tipo (Planta/Agua/Fuego). LiveData: la lista de pantalla se actualiza
    // sola cada vez que se inserta/edita/borra un Pokemon de ese tipo.
    @Query("SELECT * FROM Pokemon WHERE tipo = :tipo")
    LiveData<List<Pokemon>> loadPokemonByTipo(String tipo);
}
