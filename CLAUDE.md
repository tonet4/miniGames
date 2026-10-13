# miniGames

App Android de mini juegos para mi hija de 5 años (entretenimiento y educativos) y para mí. Un menú principal da acceso a cada juego. El primer juego es un **Memory**.

## Estado actual
👉 Mira [`docs/PROGRESS.md`](docs/PROGRESS.md) antes de hacer nada: tiene el paso actual del roadmap y lo que falta.

## Stack
- Kotlin + Jetpack Compose (Material 3)
- Arquitectura **MVI**
- **Hilt** para inyección de dependencias
- Navigation Compose con rutas **type-safe**
- Paquete `com.antonio.minigames` · minSdk 26 · móvil y tablet
- Diseño: Stitch (vía MCP), pendiente de configurar (paso 9)

## Estructura
Un solo módulo `:app`, organizado **primero por capa y luego por juego**. Detalle en [`docs/rules/architecture.md`](docs/rules/architecture.md).

```
com/antonio/minigames/
├── core/                 # mvi base, ui/theme, navigation
├── data/memory/
├── domain/memory/
├── presentation/
│   ├── menu/
│   └── memory/
└── di/
```

## Reglas (obligatorias)
| Tema | Archivo |
|------|---------|
| Arquitectura y carpetas | [`docs/rules/architecture.md`](docs/rules/architecture.md) |
| Git, ramas y commits | [`docs/rules/git.md`](docs/rules/git.md) |
| Hilt e inyección | [`docs/rules/hilt.md`](docs/rules/hilt.md) |

Las reglas crecen paso a paso: cuando se toma una decisión nueva, se añade o ajusta aquí en el mismo PR.

## Forma de trabajar
- **Pasos pequeños**: un paso del roadmap = una rama = un PR. Nunca más de uno a la vez.
- Al terminar un paso: resumen con árbol de archivos y snippets, actualizar `docs/PROGRESS.md` y **esperar la revisión** antes de seguir.
- Código, nombres, comentarios, ramas y commits en **inglés** (`feature/MG-<n>-...`, `[MG-<n>]: ...`). Docs en **español**.
- Contenido con personajes de dibujos: solo uso personal. Los temas tienen que poder cambiarse sin tocar la lógica del juego.
