package com.example.myapplication.fragmentos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.mapbox.geojson.Point;
import com.mapbox.maps.MapView;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationsUtils;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.extension.style.layers.properties.generated.IconAnchor;

// Fragmento de la ventana de Mapa. MainActivity lo mete en el FrameLayout "contenedor"
public class MapaFragment extends Fragment {

    // Ubicacion que se marca en el mapa (la misma que cameraTargetLat/Lng de fragment_mapa.xml)
    private static final double LATITUD = 43.271846691282065;
    private static final double LONGITUD = -2.948931073628467;


    // El contexto (la Activity) donde esta metido el fragmento
    private Context mContext;

    // Constructor vacio: Android lo necesita para volver a crear el fragmento (por ejemplo al girar el movil)
    public MapaFragment() {
        super();
    }

    // Android lo llama cuando el fragmento se engancha a la Activity: aqui se guarda el contexto
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    // Android lo llama solo para crear la vista del fragmento
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Convierte fragment_mapa.xml en vistas reales y las devuelve.
        // false = no pegarlo aun al contenedor, eso ya lo hace el FragmentManager
        return inflater.inflate(R.layout.fragment_mapa, container, false);
    }

    // Android lo llama justo despues de onCreateView: la vista ya existe y se pueden buscar sus componentes
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Buscar el mapa dentro de la vista del fragmento
        MapView mapView = view.findViewById(R.id.mapView);

        // 1. Sacar el "plugin" de marcas del mapa y crear con el un gestor de marcadores
        AnnotationPlugin marcas = AnnotationsUtils.getAnnotations(mapView);
        PointAnnotationManager gestorMarcadores =
                PointAnnotationManagerKt.createPointAnnotationManager(marcas, new AnnotationConfig());

        // 2. Describir el marcador: donde va, que icono tiene y por donde se "clava"
        PointAnnotationOptions marcador = new PointAnnotationOptions()
                .withPoint(Point.fromLngLat(LONGITUD, LATITUD))   // ¡OJO! primero longitud y luego latitud
                .withIconImage(crearBitmap(R.drawable.ic_marcador))
                .withIconAnchor(IconAnchor.BOTTOM);               // la punta de abajo del pin es la que marca el sitio

        // 3. Ponerlo en el mapa
        gestorMarcadores.create(marcador);
    }

    // Mapbox necesita el icono como Bitmap (una imagen de pixeles), asi que se dibuja el drawable en uno
    private Bitmap crearBitmap(int idDrawable) {
        Drawable dibujo = ContextCompat.getDrawable(mContext, idDrawable);
        Bitmap bitmap = Bitmap.createBitmap(dibujo.getIntrinsicWidth(), dibujo.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas lienzo = new Canvas(bitmap);
        dibujo.setBounds(0, 0, lienzo.getWidth(), lienzo.getHeight());
        dibujo.draw(lienzo);
        return bitmap;
    }
}
