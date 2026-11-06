# Progreso

**Paso actual:** MG-3 — pasos 2 y 3: reglas MVI + tema provisional + navegación + menú (en revisión). Siguiente: MG-4, paso 4 (dominio del Memory).

> ⏸️ **Retomar aquí (2026-11-06):** en la rama `feature/MG-3-mvi-base`, el código del menú, la navegación y el tema está **commiteado, sin push**. Falta probar la navegación en un dispositivo, subir la rama y abrir el PR contra `develop`. Pedir permiso antes de cualquier acción de git.

## Roadmap
- [x] **0. Base de conocimiento**: git, `CLAUDE.md`, `PROGRESS.md`, reglas de arquitectura y git
- [x] **1. Esqueleto Android** (MG-1, MG-2): Gradle + version catalog, Compose, Hilt, Navigation, KSP; la app vacía compila
- [x] **2. Reglas MVI** (MG-3): `rules/mvi.md`; cada pantalla tiene su propio Contract y ViewModel, sin clase base
- [x] **3. Tema provisional + navegación** (MG-3): `ui/theme`, `presentation/navigation` (NavHost type-safe), pantalla de menú, placeholder de Memory
- [ ] **4. Dominio Memory**: modelos + casos de uso + tests + `rules/testing.md`
- [ ] **5. Datos Memory**: `MemoryThemeRepository` con tema genérico (emojis/animales) + módulo Hilt
- [ ] **6. Presentación Memory**: `MemoryViewModel` + `MemoryScreen`; el juego se puede jugar
- [ ] **7. Dificultades**: 4 / 6 / 8 parejas, layout adaptable a tablet
- [ ] **8. Celebración al ganar**: animación + "otra vez"
- [ ] **9. Stitch**: alta, API key y configuración del MCP
- [ ] **10. Rediseño con Stitch**: menú + Memory + `rules/compose-ui.md`
- [ ] **11. Tema de dibujos + skill `new-game`**

## Entorno
| Herramienta | Estado |
|-------------|--------|
| Git | ✅ 2.44 |
| JDK | ✅ 22 (AGP necesita 17+) |
| Android Studio + SDK | ⚠️ Ladybug 2024.2 → máximo AGP 8.8 y SDK 35 (`%LOCALAPPDATA%\Android\Sdk`) |
| `gh` CLI | ❌ no instalado (los PRs se abren desde la web) |
| Repo GitHub | ✅ https://github.com/tonet4/miniGames (ramas `main` y `develop`) |
| Stitch | ❌ sin cuenta / API key (paso 9) |

## Decisiones
- 2026-10-13: un solo módulo `:app`, organizado por capa y luego por juego (se descartó multi-módulo por feature).
- 2026-10-13: el primer Memory usa un tema genérico; el tema de dibujos llega en el paso 11.
- 2026-10-13: Stitch se conecta cuando el juego ya funcione (paso 9).

- 2026-10-13: git flow con `develop`; ramas `feature/MG-<n>-...`, commits `[MG-<n>]: <verbo> ...` en inglés.
- 2026-10-13: las librerías se fijan a versiones compatibles con AGP 8.8 y SDK 35 (core-ktx 1.15.0, activity-compose 1.10.1, lifecycle 2.8.7) porque la plantilla de Android Studio no compilaba.

- 2026-10-13: sin ViewModel base ni `core/mvi`; cada pantalla tiene su propio Contract y ViewModel (decisión del usuario en MG-3).
- 2026-10-13: no hay carpeta `core/`; la navegación va en `ui/navigation` y el tema en `ui/theme`.
- 2026-10-15: la navegación pasa a `presentation/navigation` (cambio del usuario). `MiniGame` vive en `domain/menu/model` y el menú lo obtiene con `GetGamesUseCase`; `GameItem` (título y emoji) es un modelo de UI en `presentation/menu`.
- 2026-10-15: el ViewModel carga datos con `onStart { load() }` + `stateIn(Lazily)`, no en el `init`.
- 2026-10-13: tema provisional sin color dinámico (azul, amarillo y rosa sobre crema) hasta tener Stitch.
- 2026-10-13: textos de la UI en español, en `strings.xml`.
- 2026-10-13: versiones de MG-2, compatibles con Android Studio Ladybug: Kotlin 2.0.21, KSP 2.0.21-1.0.28, Hilt 2.55, Navigation 2.8.5, Compose BOM 2024.12.01.
- 2026-11-06: patrón de pantalla (cambio del usuario): `<Game>Screen` con ViewModel + `<Game>ScreenScaffold` privado, sin `Route`. El ViewModel expone `handle(intent)`, `uiState` y `effect`.
- 2026-11-06: los efectos usan `MutableSharedFlow` en lugar de `Channel`. Sin `replay`, se pierden si nadie los está recogiendo (decisión del usuario).
- 2026-11-06: las rutas se llaman como su pantalla (`MenuScreen`, `MemoryScreen`), sin sufijo `Destination` (cambio del usuario).

## Pendiente / dudas
- Actualizar Android Studio para poder usar AGP 8.9+ y SDK 36 (opcional).
- Instalar `gh` (`winget install GitHub.cli`) para abrir PRs desde Claude (opcional).
