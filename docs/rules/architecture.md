# Arquitectura

## Módulos
Un único módulo `:app`. No se crean módulos Gradle nuevos sin acordarlo antes.

## Paquetes: primero capa, luego juego
```
com.antonio.minigames
├── MiniGamesApp.kt           # @HiltAndroidApp
├── MainActivity.kt           # @AndroidEntryPoint, aloja el NavHost
├── ui/
│   └── theme/                # colores, tipografía, formas
├── data/
│   ├── error/                # safeApiCall: excepciones de Retrofit → AppError
│   ├── memory/               # repositorios, fuentes de datos y mappers del Memory
│   └── <game>/
├── domain/
│   ├── error/                # AppError, compartido por todos los juegos
│   ├── menu/                 # model/MiniGame.kt + usecase/MenuUseCase.kt
│   ├── memory/               # modelos, interfaces de repositorio y casos de uso del Memory
│   └── <game>/
├── presentation/
│   ├── navigation/           # Destinations.kt (rutas @Serializable) + MiniGamesNavHost.kt
│   ├── menu/                 # Contract, ViewModel, Screen y GameItem (modelo de UI)
│   ├── memory/               # Contract, ViewModel, Screen y componentes
│   └── <game>/
└── di/                       # módulos Hilt, uno por juego: MemoryModule.kt, ...
```

### Ejemplo con dos juegos (Memory + Colores)
```
data/memory/MemoryThemeRepositoryImpl.kt
data/colors/ColorsRepositoryImpl.kt
domain/memory/model/Card.kt
domain/memory/MemoryThemeRepository.kt
domain/memory/usecase/MemoryUseCase.kt
domain/colors/usecase/...
presentation/navigation/Destinations.kt  ← rutas MemoryScreen, ColorsScreen
presentation/memory/MemoryContract.kt
presentation/memory/MemoryViewModel.kt
presentation/memory/MemoryScreen.kt
presentation/colors/...
di/MemoryModule.kt
di/ColorsModule.kt
```

## Dependencias entre capas
```
presentation ──► domain ◄── data
```
- `domain` es Kotlin puro: **no** importa Android, Compose ni Hilt (como mucho `javax.inject.Inject`). Nada de `R`, `@StringRes` ni imports de `presentation`: los textos, emojis e imágenes se resuelven en `presentation` con un mapper (p. ej. `MiniGame.toGameItem()`).
- `domain` define las interfaces de repositorio y `data` las implementa.
- `presentation` solo habla con `domain` (casos de uso o repositorios), nunca con `data`.
- `di` es lo único que conoce las implementaciones de `data` y las enlaza (`@Binds`).
- Un juego **no** importa nada de otro juego. Lo que se comparte va a `ui/theme` (tema visual) o a `presentation/navigation` (navegación). No existe carpeta `core/`. No hay ViewModel base: ver [`mvi.md`](mvi.md).

## Errores
- `domain/error/AppError.kt`: `sealed class` que hereda de `Exception`, con los errores que entiende la app:
  - `NoConnection`: no hay internet
  - `ServerError`: el servidor respondió 5xx
  - `Unexpected(error)`: cualquier otra cosa; guarda la excepción original como `cause`
- `data/error/SafeApiCall.kt`: `safeApiCall { api.call() }` envuelve cada llamada de Retrofit y devuelve `Result<T>`. Convierte `IOException` en `NoConnection`, un 5xx en `ServerError` y lo demás en `Unexpected`.
- Los datasources y repositorios devuelven `Result<T>` y nunca `null` para indicar un error. Los repositorios transforman con `.map { }`.
- Los casos de uso no usan `runCatching`, porque captura la `CancellationException`. Usan `try/catch`: primero relanzan la `CancellationException`, después dejan pasar los `AppError` y el resto lo envuelven en `AppError.Unexpected`.
- `presentation` solo ve `AppError` y elige el mensaje (en `strings.xml`) para cada caso con un `when` sin `else`.

## Temas de juego
Las imágenes y textos de cada tema (genérico, dibujos…) salen de `data`. La lógica del juego no sabe de qué dibujos son las cartas.

## Nombres
| Qué | Patrón | Ejemplo |
|-----|--------|---------|
| Caso de uso | `NounUseCase`, una clase por tema con un método por acción (`verbNoun()`) | `MemoryUseCase.createBoard()`, `PokemonUseCase.getRandomPokemon()` |
| Repositorio (interfaz) | `NounRepository` | `MemoryThemeRepository` |
| Implementación | `NounRepositoryImpl` | `MemoryThemeRepositoryImpl` |
| Pantalla | `GameScreen` | `MemoryScreen` |
| Ruta de navegación | `GameScreen` | `MemoryScreen` |
| ViewModel | `GameViewModel` | `MemoryViewModel` |
| Contrato MVI | `GameContract` | `MemoryContract` |
| Módulo Hilt | `GameModule` | `MemoryModule` |

## Navegación
- Todo vive en `presentation/navigation/`:
  - `Destinations.kt`: un `@Serializable data object` (o `data class` si lleva argumentos) por pantalla, con el mismo nombre que su composable (`MemoryScreen`).
  - `MiniGamesNavHost.kt`: el único sitio que usa el `NavController`.
- Las pantallas no conocen el `NavController`: su composable `<Game>Screen` recibe lambdas (`onNavigateBack`, `onOpenGame`).
- Añadir un juego nuevo al menú supone tres cambios:
  1. Añadir un valor a `MiniGame` (en `domain/menu/model/MiniGame.kt`). `MenuUseCase.getGames()` lo devuelve solo.
  2. Añadir su rama en `MiniGame.toGameItem()` (`presentation/menu/GameItem.kt`): título y emoji.
  3. Añadir su ruta en `Destinations.kt`, su `composable<>` y su entrada en `toDestination()`.
