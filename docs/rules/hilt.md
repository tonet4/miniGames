# Hilt

## Base
- `MiniGamesApp` (`@HiltAndroidApp`) y `MainActivity` (`@AndroidEntryPoint`) ya están configurados. No se crean más Activities: todo son pantallas Compose dentro del NavHost.
- Procesador de anotaciones: **KSP** (`ksp(libs.hilt.compiler)`), nunca kapt.

## ViewModels
```kotlin
@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val createBoard: CreateBoardUseCase,
) : ViewModel()
```
En la pantalla se obtienen con `hiltViewModel()` (de `androidx.hilt:hilt-navigation-compose`).

## Módulos
- Todos en `di/`, **uno por juego**: `di/MemoryModule.kt`. Lo compartido va en `di/CoreModule.kt`.
- Las interfaces se enlazan con `@Binds` en un módulo `abstract`. `@Provides` solo para lo que no tiene constructor propio.
```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class MemoryModule {
    @Binds
    abstract fun bindMemoryThemeRepository(
        impl: MemoryThemeRepositoryImpl,
    ): MemoryThemeRepository
}
```
- Los casos de uso no necesitan módulo: basta con `@Inject constructor`.
