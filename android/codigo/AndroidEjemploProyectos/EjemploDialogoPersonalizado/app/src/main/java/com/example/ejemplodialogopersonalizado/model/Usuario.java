package com.example.ejemplodialogopersonalizado.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Usuario")
public class Usuario
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String usuario;
    private String password;

    public Usuario()
    {

    }

    @Ignore
    public Usuario(String usuario, String password)
    {
        this.usuario = usuario;
        this.password = password;
    }

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
