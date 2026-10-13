# Progreso

**Paso actual:** 0 — Base de conocimiento (en revisión)

## Roadmap
- [ ] **0. Base de conocimiento**: git, `CLAUDE.md`, `PROGRESS.md`, reglas de arquitectura y git
- [ ] **1. Esqueleto Android**: Gradle + version catalog, Compose, Hilt, Navigation, KSP; la app vacía compila
- [ ] **2. Base MVI**: `core/mvi` (State / Intent / Effect + ViewModel base) + `rules/mvi.md`
- [ ] **3. Tema provisional + navegación**: `core/ui/theme`, NavHost type-safe, pantalla de menú
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
| Android Studio + SDK | ✅ instalado (`%LOCALAPPDATA%\Android\Sdk`) |
| `gh` CLI | ❌ no instalado (hace falta para crear PRs desde Claude) |
| Repo GitHub | ❓ pendiente |
| Stitch | ❌ sin cuenta / API key (paso 9) |

## Decisiones
- 2026-10-13: un solo módulo `:app`, organizado por capa y luego por juego (se descartó multi-módulo por feature).
- 2026-10-13: el primer Memory usa un tema genérico; el tema de dibujos llega en el paso 11.
- 2026-10-13: Stitch se conecta cuando el juego ya funcione (paso 9).

## Pendiente / dudas
- Instalar `gh` (`winget install GitHub.cli`) y crear el repo en GitHub.
