# Arquitectura

## Módulos
Un único módulo `:app`. No se crean módulos Gradle nuevos sin acordarlo antes.

## Paquetes: primero capa, luego juego
```
com.antonio.minigames
├── MiniGamesApp.kt           # @HiltAndroidApp
├── MainActivity.kt           # @AndroidEntryPoint, aloja el NavHost
├── core/
│   ├── navigation/           # rutas @Serializable y NavHost
│   └── ui/theme/             # colores, tipografía, formas
├── data/
│   ├── memory/               # repositorios, fuentes de datos y mappers del Memory
│   └── <game>/
├── domain/
│   ├── memory/               # modelos, interfaces de repositorio y casos de uso del Memory
│   └── <game>/
├── presentation/
│   ├── menu/
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
domain/memory/usecase/CreateBoardUseCase.kt
domain/colors/usecase/...
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
- `domain` es Kotlin puro: **no** importa Android, Compose ni Hilt (como mucho `javax.inject.Inject`).
- `domain` define las interfaces de repositorio y `data` las implementa.
- `presentation` solo habla con `domain` (casos de uso o repositorios), nunca con `data`.
- `di` es lo único que conoce las implementaciones de `data` y las enlaza (`@Binds`).
- Un juego **no** importa nada de otro juego. Lo que se comparte (tema visual, navegación) va a `core/`. No hay ViewModel base: ver [`mvi.md`](mvi.md).

## Temas de juego
Las imágenes y textos de cada tema (genérico, dibujos…) salen de `data`. La lógica del juego no sabe de qué dibujos son las cartas.

## Nombres
| Qué | Patrón | Ejemplo |
|-----|--------|---------|
| Caso de uso | `VerbNounUseCase` | `CreateBoardUseCase` |
| Repositorio (interfaz) | `NounRepository` | `MemoryThemeRepository` |
| Implementación | `NounRepositoryImpl` | `MemoryThemeRepositoryImpl` |
| Pantalla | `GameScreen` | `MemoryScreen` |
| ViewModel | `GameViewModel` | `MemoryViewModel` |
| Contrato MVI | `GameContract` | `MemoryContract` |
| Módulo Hilt | `GameModule` | `MemoryModule` |
