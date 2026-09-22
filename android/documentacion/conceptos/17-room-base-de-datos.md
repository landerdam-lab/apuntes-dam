---
tags:
  - android
  - concepto
---

# 17 — Room: base de datos local (SQLite) en Android

> Proyecto donde se usa: [EjemploDialogoPersonalizado](../proyectos/EjemploDialogoPersonalizado.md). PDF del curso: `pdfs/1-DialogSQLitePersonalizado.pdf`.

## Qué problema resuelve

Una app necesita **guardar datos que sobrevivan** a cerrarla (usuarios, contactos, notas...). Android incluye **SQLite**, una base de datos completa dentro del móvil, guardada en un único fichero. El problema es que hablar con SQLite "a mano" obliga a escribir mucho código repetitivo (sentencias SQL en texto, cursores, conversión de filas a objetos...).

**Room** es una librería de Google que se coloca encima de SQLite y hace ese trabajo pesado. Tú escribes:

1. Una **clase Java normal** que representa una fila (la *entidad*).
2. Una **interfaz** con los métodos que quieres (insertar, borrar, buscar...).
3. Una **clase de base de datos** que lo junta todo.

Room **genera el código real** en tiempo de compilación. A esto se le llama **ORM** (*Object-Relational Mapping*): traducir entre objetos Java y filas de tablas.

## Vocabulario mínimo de bases de datos

| Término | Qué es | Ejemplo en este proyecto |
|---|---|---|
| **Tabla** | Una "hoja de Excel" con columnas fijas | La tabla `Usuario` |
| **Columna** | Un dato de cada fila | `id`, `usuario`, `password` |
| **Fila** | Un registro | Un usuario concreto: `(1, "Almi", "Almi123")` |
| **Clave primaria** | Columna que identifica cada fila de forma única | `id` |
| **Consulta / query** | Petición de datos, escrita en SQL | `SELECT * FROM Usuario` |

## Paso 1 — Dependencias (`app/build.gradle.kts`)

```kotlin
implementation("androidx.room:room-runtime:2.8.5")
annotationProcessor("androidx.room:room-compiler:2.8.5")
```

- `room-runtime` es la librería que usa la app al ejecutarse.
- `room-compiler` es el **procesador de anotaciones**: lee tus `@Entity`, `@Dao`, `@Database` y **genera** las clases que hacen el trabajo. Como el proyecto es Java (no Kotlin) se declara con `annotationProcessor`.

> El PDF usa la versión `2.6.1`; el proyecto usa `2.8.5`. Lo importante es que **las dos líneas lleven el mismo número de versión**.

## Paso 2 — La entidad (`model/Usuario.java`)

```java
@Entity(tableName = "Usuario")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String usuario;
    private String password;

    public Usuario() { }                       // Room lo necesita

    @Ignore                                    // Room ignora este constructor
    public Usuario(String usuario, String password) { ... }

    // getters y setters
}
```

Las **anotaciones** (las palabras con `@`) son etiquetas que le dan instrucciones a Room:

- **`@Entity(tableName = "Usuario")`** — "esta clase es una tabla y se llama `Usuario`". Cada atributo se convierte en una columna.
- **`@PrimaryKey(autoGenerate = true)`** — `id` es la clave primaria y Room la numera solo (1, 2, 3...). Por eso al crear un usuario nuevo no hay que darle `id`.
- **`@Ignore`** — Room solo puede usar **un** constructor para reconstruir objetos al leer la BD. Se queda con el vacío; con `@Ignore` le decimos que el de dos parámetros es solo para nuestro uso.
- **Getters y setters** — atributos `private` + métodos públicos para leerlos/cambiarlos. Room los usa para rellenar los objetos.

## Paso 3 — El DAO (`bbdd/UsuariosDao.java`)

**DAO** = *Data Access Object*: la lista de operaciones permitidas sobre la tabla. Es una **interfaz** (solo cabeceras, sin código); Room escribe el código.

```java
@Dao
public interface UsuariosDao {
    @Query("SELECT * FROM Usuario ORDER BY id")
    LiveData<List<Usuario>> loadAllUsusarios();

    @Insert  void insertUsuario(Usuario usuario);
    @Update  void updateUsuario(Usuario usuario);
    @Delete  void deleteUsuario(Usuario usuario);

    @Query("SELECT * FROM Usuario WHERE id = :id")
    Usuario loadUsuarioById(int id);

    @Query("SELECT * FROM Usuario WHERE usuario = :usu AND password = :pass")
    Usuario loadUsuarioByNamePass(String usu, String pass);
}
```

| Anotación | Qué hace | Cómo identifica la fila |
|---|---|---|
| `@Insert` | Añade una fila nueva | — (el `id` se autogenera) |
| `@Update` | Modifica una fila existente | Por su **clave primaria** (`id`) |
| `@Delete` | Borra una fila | Por su **clave primaria** (`id`) |
| `@Query("...")` | Ejecuta el SQL que escribas | Lo que pongas en el `WHERE` |

Cosas a entender:

- **`:id`, `:usu`, `:pass`** dentro del SQL son **parámetros**: se sustituyen por los argumentos del método con ese mismo nombre. Nunca se concatena el texto a mano (eso evita la *inyección SQL*).
- **`@Update` y `@Delete` necesitan el objeto completo**, no solo el id. Por eso en `RegisterActivity.eliminar()` primero se hace `loadUsuarioById(id)` y después `deleteUsuario(usu)`.
- Si una consulta que devuelve **un** objeto no encuentra nada, devuelve **`null`**. Así funciona el login: `loadUsuarioByNamePass(...)` da `null` si nombre y contraseña no coinciden con ninguna fila.
- **`LiveData<List<Usuario>>`** es un tipo especial que **avisa cuando los datos cambian**. Se explica en [20-executors-y-livedata](20-executors-y-livedata.md).

## Paso 4 — La base de datos (`bbdd/AppDatabase.java`)

```java
@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).build();
            }
        }
        return sInstance;
    }

    public abstract UsuariosDao usuariosDao();
}
```

- **`@Database(entities = {...}, version = 1)`** — lista qué tablas tiene. `version` sirve para las migraciones: si algún día cambias las columnas, subes el número y explicas a Room cómo pasar de una versión a otra. `exportSchema = false` = "no guardes un fichero de historial del esquema".
- **`abstract`** — no se puede hacer `new AppDatabase()`. Room crea por debajo una subclase con el código real.
- **`public abstract UsuariosDao usuariosDao()`** — la puerta de entrada: `mDb.usuariosDao().insertUsuario(...)`.
- **`Room.databaseBuilder(...).build()`** — construye (o abre, si ya existía) el fichero de la base de datos. Solo se le da un nombre; el fichero queda en `/data/data/<paquete>/databases/AppDatabase`.
- **`getApplicationContext()`** — el contexto de toda la app, no el de una pantalla. Así la base de datos no "retiene" una `Activity` que ya se cerró (fuga de memoria).

### El patrón Singleton

Abrir la base de datos es caro, y tener **dos** objetos abiertos sobre el mismo fichero da problemas. El patrón **Singleton** garantiza que solo existe **una instancia** para toda la app:

1. El constructor no es accesible (clase `abstract`).
2. Un atributo `static` guarda la única instancia (`sInstance`).
3. Un método `static` (`getInstance`) la crea la primera vez y después siempre devuelve la misma.
4. `synchronized (LOCK)` es un "candado": si dos hilos llegan a la vez, uno espera a que el otro salga del bloque.

> **Detalle técnico:** en el código actual la comprobación `if (sInstance == null)` está **fuera** del `synchronized` y no se repite dentro. Si dos hilos pasan a la vez la comprobación, los dos crearán una instancia, uno detrás de otro. En esta app casi nunca ocurre, pero la versión "de libro" repite la comprobación dentro del bloque (*double-checked locking*).

## Paso 5 — Usarla desde una pantalla

```java
mDb = AppDatabase.getInstance(getApplicationContext());     // obtener la BD

AppExecutors.getInstance().getDiskIO().execute(new Runnable() {   // en 2.º plano
    @Override public void run() {
        mDb.usuariosDao().insertUsuario(new Usuario(user, password));
    }
});
```

### La regla de oro: nunca en el hilo principal

Room **lanza una excepción** si intentas insertar/consultar/borrar en el hilo principal (el que dibuja la pantalla), porque una consulta lenta congelaría la app. Toda operación se hace en un **hilo secundario** con `AppExecutors` (ver [20-executors-y-livedata](20-executors-y-livedata.md)). La única excepción son las consultas que devuelven `LiveData`: Room ya las ejecuta él solo en segundo plano.

## Cómo ver la base de datos real

En Android Studio:

- **View → Tool Windows → App Inspection → Database Inspector**: tablas y filas en directo mientras la app corre.
- **View → Tool Windows → Device Explorer**: `/data/data/com.example.ejemplodialogopersonalizado/databases/` para ver el fichero físico.

## Errores típicos

| Síntoma | Causa probable |
|---|---|
| `Cannot access database on the main thread` | Operación de BD fuera de `getDiskIO().execute(...)` |
| Error de compilación en las clases `_Impl` | Falta `annotationProcessor` o las dos versiones de Room no coinciden |
| `Room cannot pick a constructor` | Dos constructores sin `@Ignore` en uno |
| La app se cierra tras cambiar las columnas | Cambiaste la entidad sin subir `version` (en desarrollo: desinstala la app) |
| `@Update`/`@Delete` no hacen nada | El objeto tiene `id` incorrecto (p. ej. `0` o `-1`), no coincide con ninguna fila |

## Ver también
- [20 — Executors y LiveData](20-executors-y-livedata.md) — por qué y cómo se sale del hilo principal, y cómo se refresca la lista sola.
- [07 — Adaptadores](07-adaptadores.md) — cómo se muestran los datos leídos en un `ListView`.
- [12 — Hilos, a fondo](12-hilos-en-profundidad.md) — la teoría de hilo principal vs hilos de fondo.
- [00 — Programación básica](00-programacion-basica.md) — clases, interfaces, `static`, `null`.

## 🏋️ Practica esto

- [Nivel 6 — ejercicio 6.3](../ejercicios/06-nivel-6-dialogos-y-room.md) (app de notas con Room) y [Simulacro C — Login y registro](../ejercicios/08-simulacros-de-examen.md)
- [Nivel 7 — ejercicio 7.5](../ejercicios/07-nivel-7-mejoras-del-proyecto.md) (rechazar usuarios repetidos con una `@Query` nueva)
