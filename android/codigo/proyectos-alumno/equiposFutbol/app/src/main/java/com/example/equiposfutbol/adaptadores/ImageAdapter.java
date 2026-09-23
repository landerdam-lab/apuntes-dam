package com.example.equiposfutbol.adaptadores;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.equiposfutbol.model.Equipos;
import com.example.equiposfutbol.model.Jugador;

import java.util.ArrayList;

// Adapter pensado para la rejilla de jugadores — pero SIN TERMINAR. La clase existe y "compila" bien
// porque implementa todos los métodos que exige BaseAdapter, pero no hace nada útil todavía: si
// llegara a usarse de verdad (asignándolo a un GridView), la app CRASHEARÍA en cuanto intentara
// dibujar cualquier fila, porque getView() siempre devuelve null y un GridView espera siempre una
// View real (ver documentacion/proyectos/equiposFutbol.md para el detalle completo).
public class ImageAdapter extends BaseAdapter {
    private ArrayList<Jugador> jugadores;
    private final Context contexto;
    public ImageAdapter(Context contexto, ArrayList<Jugador>jugadores) {
        this.contexto = contexto;
        this.jugadores = jugadores;
    }

    @Override
    public int getCount() {
        return jugadores.size();
    }

    // OJO: debería devolver jugadores.get(position) (el jugador real de esa posición), pero
    // de momento siempre devuelve null — sin terminar.
    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // OJO: debería inflar un layout de celda y rellenarlo con los datos del jugador (igual que hace
    // EquiposAdapter.getView), pero de momento siempre devuelve null — sin terminar.
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
