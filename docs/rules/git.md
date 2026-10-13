# Git

## Ramas
- `main` siempre compila. No se hace commit directo a `main`.
- Una rama por paso del roadmap: `<tipo>/paso-<n>-<descripcion-corta>`
  - `chore/paso-0-base-conocimiento`
  - `feat/paso-4-dominio-memory`

## Commits: Conventional Commits, en español
```
<tipo>(<ámbito>): <descripción en imperativo>
```
| Tipo | Uso |
|------|-----|
| `feat` | funcionalidad nueva |
| `fix` | corrección de errores |
| `refactor` | cambio sin cambiar el comportamiento |
| `test` | tests |
| `docs` | documentación y reglas |
| `chore` | build, config, dependencias |

Ámbitos: `memory`, `menu`, `core`, `build`, `docs`…

Ejemplos:
```
docs(rules): añadir reglas de arquitectura y git
feat(memory): crear tablero barajado según dificultad
test(memory): comprobar detección de pareja
```

## Pull Requests
- Un PR por paso. Título = el commit principal.
- Descripción: qué se hizo, árbol de archivos nuevos y cómo probarlo.
- Antes de abrir el PR: compila (`./gradlew assembleDebug`) y los tests pasan.
- El PR lo revisa y lo mergea el usuario.
- Al cerrar el paso se marca en `docs/PROGRESS.md`.
