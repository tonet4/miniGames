# Tests

## Qué se prueba
| Capa | Qué | Ejemplo |
|------|-----|---------|
| `domain` | Casos de uso: cada regla del juego y cada caso que se ignora | `MemoryUseCaseTest` |
| `presentation` | ViewModels: ver [`mvi.md`](mvi.md) | `MenuViewModelTest` |
| `data` | Conversión de errores | `SafeApiCallTest` |

## Reglas
- JUnit 4. Los tests van en `src/test/`, en el mismo paquete que la clase que prueban, y se llaman `<Clase>Test`.
- Nombre del test en inglés, entre backticks y describiendo el comportamiento: `` `flipCard ignores a third card` ``.
- El dominio se prueba sin Android, sin mocks y sin corrutinas si la función no es `suspend`.
- El azar se recibe como parámetro (`random: Random = Random.Default`). Los tests usan una semilla fija o un tablero ordenado escrito a mano.
- Se ejecutan con `./gradlew testDebugUnitTest`.
