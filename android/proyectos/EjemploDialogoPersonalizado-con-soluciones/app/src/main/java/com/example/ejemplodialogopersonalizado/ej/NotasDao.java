package com.example.ejemplodialogopersonalizado.ej;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao //DATA ACCESS OBJECT
public interface NotasDao {

    @Query("SELECT * FROM Nota ORDER BY id")
    LiveData<List<Nota>> loadAllNotas();

    @Insert
    void insertNota(Nota nota);

    @Update
    void updateNota(Nota nota);

    @Delete
    void deleteNota(Nota nota);

    @Query("SELECT * FROM Nota WHERE id = :id") //:id es el parametro del metodo
    Nota loadNotaById(int id);

    @Query("SELECT * FROM Nota WHERE titulo = :titulo")
    Nota loadNotaByTitulo(String titulo);
}
