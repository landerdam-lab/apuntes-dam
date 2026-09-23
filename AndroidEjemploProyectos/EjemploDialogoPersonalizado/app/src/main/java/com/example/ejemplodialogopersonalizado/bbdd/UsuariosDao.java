package com.example.ejemplodialogopersonalizado.bbdd;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ejemplodialogopersonalizado.model.Usuario;

import java.util.List;

@Dao
public interface UsuariosDao
{
    @Query("SELECT * FROM Usuario ORDER BY id")
    LiveData<List<Usuario>> loadAllUsuarios();

    @Insert
    void insertUsuario(Usuario usuario);

    @Update
    void updateUsuario(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE id = :id")
    Usuario loadUsuarioById(int id);

    @Query("SELECT * FROM Usuario WHERE usuario = :usu AND password = :pass")
    Usuario loadUsuarioByNamePass(String usu, String pass);
}
