# MVI

## Flujo
```
 Screen ──Intent──► ViewModel.onIntent()
   ▲                     │
   │                     ├─ setState { copy(...) }  ──► state: StateFlow<State>
   │                     └─ sendEffect(...)         ──► effects: Flow<Effect>
   └───────── observa state y effects ◄─────────────┘
```
- **State**: todo lo que la pantalla pinta. Es un `data class` inmutable con valores por defecto.
- **Intent**: lo que hace el usuario (`CardTapped`, `RestartClicked`…). Es un `sealed interface`.
- **Effect**: un evento que ocurre una sola vez (navegar, sonido, vibración). Es un `sealed interface`. **Nunca** se guarda en el State.

## Base (en `core/mvi/`)
| Archivo | Qué es |
|---------|--------|
| `MviContract.kt` | interfaces `UiState`, `UiIntent`, `UiEffect` |
| `MviViewModel.kt` | ViewModel base: `state`, `effects`, `onIntent()`, `setState {}`, `sendEffect()` |
| `CollectEffects.kt` | composable que escucha los effects solo mientras la pantalla está visible |

## Un juego = 3 archivos en `presentation/<game>/`
```
presentation/memory/
├── MemoryContract.kt    ← State + Intent + Effect
├── MemoryViewModel.kt
└── MemoryScreen.kt      ← MemoryRoute (con ViewModel) + MemoryScreen (sin ViewModel)
```

### Contract
```kotlin
data class MemoryState(
    val cards: List<CardUi> = emptyList(),
    val moves: Int = 0,
    val isFinished: Boolean = false,
) : UiState

sealed interface MemoryIntent : UiIntent {
    data class CardTapped(val id: Int) : MemoryIntent
    data object RestartClicked : MemoryIntent
    data object BackClicked : MemoryIntent
}

sealed interface MemoryEffect : UiEffect {
    data object NavigateBack : MemoryEffect
}
```

### ViewModel
```kotlin
@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val createBoard: CreateBoardUseCase,
) : MviViewModel<MemoryState, MemoryIntent, MemoryEffect>(MemoryState()) {

    override fun onIntent(intent: MemoryIntent) {
        when (intent) {
            is MemoryIntent.CardTapped -> onCardTapped(intent.id)
            MemoryIntent.RestartClicked -> setState { MemoryState(cards = createBoard()) }
            MemoryIntent.BackClicked -> sendEffect(MemoryEffect.NavigateBack)
        }
    }
}
```

### Screen: Route + Screen
```kotlin
@Composable
fun MemoryRoute(onBack: () -> Unit, viewModel: MemoryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            MemoryEffect.NavigateBack -> onBack()
        }
    }
    MemoryScreen(state = state, onIntent = viewModel::onIntent)
}

@Composable
fun MemoryScreen(state: MemoryState, onIntent: (MemoryIntent) -> Unit) { /* solo UI */ }
```

## Reglas
- `MemoryScreen` **no** conoce el ViewModel: recibe `state` y `onIntent`. Así se puede previsualizar con `@Preview` y testear.
- La pantalla solo envía Intents. No llama a funciones del ViewModel con otros nombres.
- El State solo se cambia con `setState { copy(...) }`, nunca de otra forma.
- La lógica del juego (barajar, comprobar pareja…) va en `domain`, no en el ViewModel. El ViewModel orquesta.
- La navegación sale como Effect y la Route la convierte en lambda (`onBack`). El ViewModel nunca recibe un `NavController`.

## Tests
Los ViewModels se testean con `MainDispatcherRule` (en `src/test/.../core/mvi/`). Ejemplo: `MviViewModelTest`.
