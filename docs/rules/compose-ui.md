# Diseño y Compose UI

Estilo **retro**: bordes gruesos, esquinas recortadas, sombra dura y tipografía de píxel.
- **Claro**: papel crema con verde azulado, mostaza y tomate.
- **Oscuro**: recreativa de noche con cian, amarillo y rosa neón.

La primera vez sigue el modo del sistema; después manda lo que se elija con el botón de la home (`ThemeToggle`), que se guarda en DataStore (`SettingsUseCase`). `MainActivity` lo aplica a toda la app. Todo el tema vive en `ui/theme/`.

## Reglas
- Colores siempre con `MaterialTheme.colorScheme.*`. Nunca `Color(0x...)` ni los nombres de `Color.kt` fuera de `ui/theme`.
- Textos siempre con `MaterialTheme.typography.*`. Sin `fontSize` ni `FontWeight` sueltos: las fuentes solo tienen un peso.
- Formas con `MaterialTheme.shapes.*`.
- Encima de un color se usa su pareja `on*` (`secondary` → `onSecondary`).
- Los componentes llevan `@PreviewLightDark`, para ver los dos modos.

## Colores
| Rol | Uso |
|-----|-----|
| `primary` | Reverso de las cartas, elemento principal |
| `primaryContainer` | Fondo de las portadas del menú |
| `secondary` | Botones, rótulos, parejas encontradas |
| `tertiary` | Títulos destacados, victoria, flecha de volver |
| `background` / `surface` | Fondo de pantalla / paneles y barra superior |
| `outline` | Bordes y sombras |

## Tipografía
| Estilos | Fuente |
|---------|--------|
| `display*`, `headline*`, `title*` | Press Start 2P (textos cortos) |
| `body*`, `label*` | VT323 (textos y botones) |

Las dos tienen licencia OFL (copia en `docs/licenses/`).

## Piezas compartidas (`presentation/shared/components/`)
- `Modifier.retroBackground()`: fondo de puntos de cada `<Game>Content`.
- `Modifier.retroFrame()`: borde, esquinas y sombra dura.
- `RetroButton`, `CustomTopBar` (título centrado).

## Splash
`Theme.Minigames.Splash` (`core-splashscreen`) con `drawable/ic_gamepad`. Sus colores están en `values/colors.xml` y `values-night/colors.xml`: si cambia el fondo del tema, hay que cambiar `window_background`.

## Icono de la app
Icono adaptativo (`ic_launcher_foreground` + `ic_launcher_background`) con el mismo mando del splash, en colores fijos.

## Portadas del menú
`GameItem.coverUrl`. Mientras carga, o si no hay internet, se ve `ic_gamepad`.
