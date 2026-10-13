# MVI

## Flujo
```
 Screen ──Intent──► ViewModel.onIntent()
   ▲                     │
   │                     ├─ _state.update { it.copy(...) } ──► state: StateFlow<State>
   │                     └─ _effects.send(...)             ──► effects: Flow<Effect>
   └───────── observa state y effects ◄─────────────────────┘
```
- **State**: todo lo que la pantalla pinta. Es un `data class` inmutable con valores por defecto.
- **Intent**: lo que hace el usuario (`CardTapped`, `RestartClicked`…). Es un `sealed interface`.
- **Effect**: un evento que ocurre una sola vez (navegar, sonido, vibración). Es un `sealed interface`. **Nunca** se guarda en el State.

## Sin clase base
**No hay ViewModel base ni interfaces compartidas.** Cada pantalla tiene **su propio Contract y su propio ViewModel**, que extiende directamente de `ViewModel()`.

## Cada pantalla = 3 archivos en `presentation/<game>/`
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
)

sealed interface MemoryIntent {
    data class CardTapped(val id: Int) : MemoryIntent
    data object RestartClicked : MemoryIntent
    data object BackClicked : MemoryIntent
}

sealed interface MemoryEffect {
    data object NavigateBack : MemoryEffect
}
```

### ViewModel
```kotlin
@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val createBoard: CreateBoardUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MemoryState())
    val state: StateFlow<MemoryState> = _state.asStateFlow()

    private val _effects = Channel<MemoryEffect>(Channel.BUFFERED)
    val effects: Flow<MemoryEffect> = _effects.receiveAsFlow()

    fun onIntent(intent: MemoryIntent) {
        when (intent) {
            is MemoryIntent.CardTapped -> onCardTapped(intent.id)
            MemoryIntent.RestartClicked -> _state.update { MemoryState(cards = createBoard()) }
            MemoryIntent.BackClicked -> viewModelScope.launch { _effects.send(MemoryEffect.NavigateBack) }
        }
    }
}
```

### Screen: Route + Screen
```kotlin
@Composable
fun MemoryRoute(onBack: () -> Unit, viewModel: MemoryViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                MemoryEffect.NavigateBack -> onBack()
            }
        }
    }

    MemoryScreen(state = state, onIntent = viewModel::onIntent)
}

@Composable
fun MemoryScreen(state: MemoryState, onIntent: (MemoryIntent) -> Unit) { /* solo UI */ }
```

## Reglas
- Una única función pública de entrada: `onIntent(intent)`. La pantalla solo envía Intents.
- `_state` y `_effects` son privados. Se exponen como `StateFlow` y `Flow` de solo lectura.
- El State solo se cambia con `_state.update { it.copy(...) }`.
- `MemoryScreen` **no** conoce el ViewModel: recibe `state` y `onIntent`. Así se puede previsualizar con `@Preview` y testear.
- La lógica del juego (barajar, comprobar pareja…) va en `domain`, no en el ViewModel. El ViewModel orquesta.
- La navegación sale como Effect y la Route la convierte en lambda (`onBack`). El ViewModel nunca recibe un `NavController`.
