package com.example.ejemplodialogopersonalizado.bbdd;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ejemplodialogopersonalizado.model.Usuario;

import java.util.List;

/*
 * DAO = Data Access Object (objeto de acceso a datos).
 * Es una INTERFAZ donde listamos las operaciones que queremos hacer sobre la tabla Usuario.
 * Solo escribimos la cabecera del metodo: Room genera el codigo real por nosotros.
 */
@Dao
public interface UsuariosDao
{
    //Devuelve TODOS los usuarios ordenados por id.
    //LiveData = "dato observable": cuando la tabla cambia, avisa automaticamente a quien lo observe
    //(asi la lista de la pantalla se actualiza sola al insertar/borrar/actualizar).
    @Query("SELECT * FROM Usuario ORDER BY id")
    LiveData<List<Usuario>> loadAllUsusarios();

    //INSERT: guarda un usuario nuevo en la tabla
    @Insert
    void insertUsuario(Usuario usuario);

    //UPDATE: modifica un usuario que ya existe (lo busca por su clave primaria, el id)
    @Update
    void updateUsuario(Usuario usuario);

    //DELETE: borra ese usuario de la tabla
    @Delete
    void deleteUsuario(Usuario usuario);

    //Busca un usuario por su id. :id se sustituye por el parametro "id" del metodo
    @Query("SELECT * FROM Usuario WHERE id = :id")
    Usuario loadUsuarioById(int id);

    //Busca un usuario solo por su nombre (null si no existe)
    @Query("SELECT * FROM Usuario WHERE usuario = :usu")
    Usuario loadUsuarioByName(String usu);

    //Busca un usuario por nombre y password (sirve para comprobar un login).
    //Devuelve null si no existe ninguno que coincida.
    @Query("SELECT * FROM Usuario WHERE usuario = :usu AND password = :pass")
    Usuario loadUsuarioByNamePass(String usu, String pass);
}
