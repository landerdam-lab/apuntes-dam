package com.example.ejemplodialogopersonalizado.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/*
 * MODELO / ENTIDAD
 * Representa un usuario. Al llevar @Entity, Room crea una TABLA en la base de datos
 * donde cada atributo (id, usuario, password) es una COLUMNA y cada objeto Usuario es una FILA.
 */
@Entity(tableName = "Usuario") //Nombre de la tabla en la base de datos
public class Usuario
{
    //Clave primaria: identifica de forma unica a cada usuario.
    //autoGenerate = true -> Room le asigna el numero solo (1, 2, 3...) al insertar
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String usuario;
    private String password;

    //Constructor vacio: Room lo necesita para poder crear objetos Usuario al leer de la BD
    public Usuario()
    {

    }

    //Constructor que usamos nosotros para crear un usuario nuevo (sin id, lo pone Room)
    //@Ignore le dice a Room "este constructor no lo uses", porque solo puede usar uno
    @Ignore
    public Usuario(String usuario, String password)
    {
        this.usuario = usuario;
        this.password = password;
    }

    //GETTERS y SETTERS: metodos para leer (get) y modificar (set) cada atributo privado.
    //Room tambien los usa para leer/escribir los datos.
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsuario()
    {
        return usuario;
    }

    public void setUsuario(String usuario)
    {
        this.usuario = usuario;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
