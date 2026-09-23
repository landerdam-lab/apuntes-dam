---
tags:
  - android
  - reutilizable
  - tema/room
aliases:
  - Room reutilizable
  - Plantilla Room
---

# 07 — Base de datos con Room (plantilla completa)

> Teoría: [17 — Room](../02-conceptos/17-room-base-de-datos.md) · [20 — Executors y LiveData](../02-conceptos/20-executors-y-livedata.md) · Proyectos: [EjemploDialogoPersonalizado](../04-proyectos-profesor/EjemploDialogoPersonalizado.md), [EjercicioPokemon](../04-proyectos-profesor/EjercicioPokemon.md)

Receta en 5 archivos. Sustituye `Nota` por tu entidad (`Usuario`, `Pokemon`…).

## 0. Gradle (`app/build.gradle.kts`) ✅ Reutilizable
```kotlin
dependencies {
    implementation("androidx.room:room-runtime:2.8.5")
    annotationProcessor("androidx.room:room-compiler:2.8.5")   // MISMA versión
}
```

## 1. Entidad ✅ Reutilizable
```java
@Entity(tableName = "Nota")
public class Nota implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String titulo;
    private String texto;

    public Nota() { }                                   // el que usa Room
    @Ignore                                             // el nuestro: Room lo ignora
    public Nota(String titulo, String texto) { this.titulo = titulo; this.texto = texto; }

    public int getId() { return id; }            public void setId(int id) { this.id = id; }
    public String getTitulo() { return titulo; } public void setTitulo(String t) { this.titulo = t; }
    public String getTexto() { return texto; }   public void setTexto(String t) { this.texto = t; }
}
```

## 2. DAO ✅ Reutilizable
```java
@Dao
public interface NotaDao {
    @Query("SELECT * FROM Nota ORDER BY id DESC")
    LiveData<List<Nota>> getTodas();                       // LiveData: se puede llamar desde el hilo principal

    @Query("SELECT * FROM Nota WHERE titulo LIKE '%' || :texto || '%'")
    LiveData<List<Nota>> buscar(String texto);             // búsqueda por texto

    @Query("SELECT * FROM Nota WHERE id = :id")
    Nota getPorId(int id);                                 // valor directo: SIEMPRE en segundo plano

    @Query("SELECT COUNT(*) FROM Nota WHERE titulo = :titulo")
    int contarPorTitulo(String titulo);                    // para evitar duplicados

    @Insert long insertar(Nota n);                         // devuelve el id generado
    @Update void actualizar(Nota n);
    @Delete void borrar(Nota n);
}
```

## 3. Database (Singleton, versión completa) ✅ Reutilizable
```java
@Database(entities = {Nota.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instancia;              // volatile: visible entre hilos

    public static AppDatabase getInstance(Context context) {
        if (instancia == null) {
            synchronized (AppDatabase.class) {
                if (instancia == null) {                        // 2.ª comprobación dentro del lock
                    instancia = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app.db")
                            .fallbackToDestructiveMigration(true)   // si cambias la entidad y subes version: borra y recrea (solo para clase)
                            .build();
                }
            }
        }
        return instancia;
    }
    public abstract NotaDao notaDao();
}
```

## 4. `AppExecutors` **corregido** ✅ Reutilizable
```java
public class AppExecutors {
    private static volatile AppExecutors instancia;
    private final Executor diskIO, mainThread, networkIO;

    private AppExecutors(Executor diskIO, Executor mainThread, Executor networkIO) {
        this.diskIO = diskIO; this.mainThread = mainThread; this.networkIO = networkIO;
    }

    public static AppExecutors getInstance() {
        if (instancia == null) {
            synchronized (AppExecutors.class) {
                if (instancia == null) {
                    instancia = new AppExecutors(
                            Executors.newSingleThreadExecutor(),   // diskIO
                            new MainThreadExecutor(),              // mainThread ✔ (en el PDF estaba cruzado)
                            Executors.newFixedThreadPool(3));      // networkIO
                }
            }
        }
        return instancia;
    }
    public Executor diskIO() { return diskIO; }
    public Executor mainThread() { return mainThread; }
    public Executor networkIO() { return networkIO; }

    private static class MainThreadExecutor implements Executor {
        private final Handler handler = new Handler(Looper.getMainLooper());
        @Override public void execute(Runnable r) { handler.post(r); }
    }
}
```

## 5. Usarlo en una Activity ✅ Reutilizable
```java
AppDatabase db = AppDatabase.getInstance(this);

// LEER (se refresca solo)
db.notaDao().getTodas().observe(this, notas -> adapter.actualizar(notas));

// INSERTAR
AppExecutors.getInstance().diskIO().execute(() -> db.notaDao().insertar(new Nota(titulo, texto)));

// CONSULTAR y volver a la pantalla (p. ej. login o comprobar duplicados)
AppExecutors.getInstance().diskIO().execute(() -> {
    int n = db.notaDao().contarPorTitulo(titulo);                 // en segundo plano
    AppExecutors.getInstance().mainThread().execute(() -> {       // en el hilo principal
        if (n > 0) etTitulo.setError("Ya existe");
        else guardar();
    });
});

// BORRAR con confirmación
new AlertDialog.Builder(this).setMessage("¿Borrar?")
        .setPositiveButton("Sí", (d, w) ->
                AppExecutors.getInstance().diskIO().execute(() -> db.notaDao().borrar(nota)))
        .setNegativeButton("No", null).show();
```

> [!warning] ⚠️ Cuidado: los tres errores de Room más típicos
> 1. `Cannot access database on the main thread` → falta `diskIO().execute(...)`.
> 2. Cambiaste la entidad y la app se cierra al abrir (*"Room cannot verify the data integrity"*) → sube `version` y usa `fallbackToDestructiveMigration(true)`, o desinstala la app del emulador.
> 3. `cannot find implementation for AppDatabase` → falta `annotationProcessor` o las versiones no coinciden.

> [!tip] 💡 Recomendación
> Para ver el contenido de la BD mientras la app corre: **App Inspection → Database Inspector** en Android Studio ([Guía 01](../01-guias/01-ejecutar-la-app.md)).
