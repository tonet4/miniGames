# MVI

## Flujo
```
 Screen ──Intent──► ViewModel.handle()
   ▲                     │
   │                     ├─ _uiState.update { it.copy(...) } ──► uiState: StateFlow<UiState>
   │                     └─ _uiEffect.emit(...)              ──► effect: SharedFlow<Effect>
   └───────── observa uiState y effect ◄──────────────────────┘
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
└── MemoryScreen.kt      ← MemoryScreen (con ViewModel) + MemoryScreenScaffold (privado, sin ViewModel)
```

### Contract
```kotlin
data class MemoryUiState(
    val cards: List<CardUi> = emptyList(),
    val moves: Int = 0,
    val isFinished: Boolean = false,
)

sealed interface MemoryIntent {
    data class CardTapped(val id: Int) : MemoryIntent
    data object RestartClicked : MemoryIntent
    data object NavigateBack : MemoryIntent
}

sealed interface MemoryEffect {
    data object OnNavigateBack : MemoryEffect
}
```

### ViewModel
```kotlin
@HiltViewModel
class MemoryViewModel @Inject constructor(
    private val createBoard: CreateBoardUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MemoryUiState())
    val uiState: StateFlow<MemoryUiState> = _uiState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<MemoryEffect>()
    val effect = _uiEffect.asSharedFlow()

    fun handle(intent: MemoryIntent) {
        when (intent) {
            is MemoryIntent.CardTapped -> onCardTapped(intent.id)
            MemoryIntent.RestartClicked -> _uiState.update { MemoryUiState(cards = createBoard()) }
            MemoryIntent.NavigateBack -> viewModelScope.launch { _uiEffect.emit(MemoryEffect.OnNavigateBack) }
        }
    }
}
```
`MutableSharedFlow` sin `replay` **pierde** el efecto si nadie lo está recogiendo en ese momento. La pantalla lo recoge desde que entra en composición, y en los tests hay que suscribirse antes de enviar el Intent.

### Carga inicial
Los datos iniciales no se cargan en el `init`: se cargan cuando la UI empieza a recoger el estado. Así crear el ViewModel en un test no dispara nada.
```kotlin
private val _uiState = MutableStateFlow(MenuUiState())
val uiState: StateFlow<MenuUiState> = _uiState
    .onStart { loadMenu() }
    .stateIn(viewModelScope, SharingStarted.Lazily, _uiState.value)
```
- `Lazily`: carga una sola vez. Con `WhileSubscribed` se recargaría al volver a la pantalla.
- En los tests hay que recoger el estado antes de comprobarlo: `backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { vm.uiState.collect {} }`.

### Screen: Screen + Scaffold
```kotlin
@Composable
fun MemoryScreen(viewModel: MemoryViewModel = hiltViewModel(), onNavigateBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MemoryEffect.OnNavigateBack -> onNavigateBack()
            }
        }
    }

    MemoryScreenScaffold(uiState = uiState, onIntent = viewModel::handle)
}

@Composable
private fun MemoryScreenScaffold(uiState: MemoryUiState, onIntent: (MemoryIntent) -> Unit) { /* solo UI */ }
```

## Reglas
- Una única función pública de entrada: `handle(intent)`. La pantalla solo envía Intents.
- `_uiState` y `_uiEffect` son privados. Se exponen como `StateFlow` y `SharedFlow` de solo lectura.
- El State solo se cambia con `_uiState.update { it.copy(...) }`.
- `MemoryScreenScaffold` **no** conoce el ViewModel: recibe `uiState` y `onIntent`. La `@Preview` usa el Scaffold, porque `hiltViewModel()` no funciona en previews.
- La lógica del juego (barajar, comprobar pareja…) va en `domain`, no en el ViewModel. El ViewModel orquesta.
- La navegación sale como Effect y `MemoryScreen` la convierte en lambda (`onNavigateBack`). El ViewModel nunca recibe un `NavController`.

## Tests
Cada ViewModel tiene su test en `src/test/.../presentation/<game>/`. Se usa `MainDispatcherRule` (en `src/test/.../testutil/`) para que `viewModelScope` funcione. Ejemplo: `MenuViewModelTest`.
