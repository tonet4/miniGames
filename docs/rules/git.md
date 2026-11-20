# Git

## Ramas
```
main      ← versiones estables (se mergea develop cuando hay algo jugable)
develop   ← rama de integración: aquí llegan los PRs de cada paso
  └── feature/MG-4-memory-domain   ← una rama por tarea, sale de develop
```
- `main` y `develop` siempre compilan. No se hace commit directo a ninguna de las dos.
- Una rama por tarea, **creada desde `develop`**, y su PR va **contra `develop`**.
- Formato (siempre en **inglés**, kebab-case):
  ```
  feature/MG-<n>-<short-description>
  ```
  - `feature/MG-1-first-commit`
  - `feature/MG-4-memory-domain`
- `MG-<n>` es el identificador de la tarea. Se incrementa en cada rama nueva.

## Commits (siempre en inglés)
```
[MG-<n>]: <description>
```
Ejemplos:
```
[MG-1]: add initial project setup
[MG-4]: create shuffled board by difficulty
[MG-4]: add match detection tests
[MG-6]: delete unused preview
[MG-7]: update grid columns for tablets
```
- Mismo `MG-<n>` que la rama.
- La descripción **siempre empieza por un verbo en presente**: `add`, `delete`, `update`, `fix`, `create`, `move`, `rename`, `refactor`…
- Corta y en minúsculas.

## Pull Requests
- Un PR por tarea, contra `develop`. Título: `[MG-<n>]: <description>`.
- Descripción: qué se hizo, árbol de archivos nuevos y cómo probarlo.
- Antes de abrir el PR: compila (`./gradlew assembleDebug`) y los tests pasan.
- El PR lo revisa y lo mergea el usuario.
- Al cerrar la tarea se marca en `docs/PROGRESS.md`.
