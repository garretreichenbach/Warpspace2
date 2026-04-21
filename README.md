# WarpSpace 2

A StarMade mod that replaces the vanilla "teleport" jump drive with a
**parallel dimension** called *Warpspace* — a compressed copy of realspace
where every 10 realspace sectors fit into 1 warp sector. Trips through warp
are fast but still physical, so FTL travel becomes predictable, shared, and
interceptable.

Successor to the original [Warpspace](https://github.com/IR0NSIGHT/Warpspace)
by IR0NSIGHT, rebuilt on SpongePowered Mixin and the modern StarLoader APIs.

---

## Core concept

Instead of vanishing and reappearing at your waypoint, your ship drops into
Warpspace, physically travels the (shorter) distance, and drops back out at
a droppoint near your destination.

- **Shared dimension.** Every ship in warp is in the same warpspace; you can
  intercept or be intercepted mid-trip.
- **Predictable timing.** Trips have a real duration, so defenders can
  respond to incoming threats.
- **Droppoint grid.** Warp exits land on a coarse grid. Warp Beacons let
  stations override the grid for their region.
- **Reworked inhibitors.** Inhibitor chambers can deny warp jumps within a
  3-sector radius, with a reactor-size rule to stop small ships from
  shutting down capital traffic.

## Features

- **Warpspace dimension** with configurable warp-to-realspace scale (default
  10×) and per-chamber flight speed multipliers.
- **Warp Beacon chambers** that redirect nearby droppoints to a station's
  sector.
- **Rewritten HUD** in the bottom-right corner — icon set for dimension,
  jump state, and inhibition.
- **Map overlays** — natural/shifted droppoints in realspace, scaled galaxy
  view in warp.
- **In-game guides** accessible from the main menu's Guides dialog (see
  [Documentation](#documentation)).
- **Two-layer config**: server-authoritative settings (auto-synced to
  clients) and client-local preferences.

## Install

1. Open the in-game mod browser.
2. Find **WarpSpace 2** and click **Install**.
3. Make sure the mod is enabled in the mod list.

No external dependencies or class-resizing required — this version uses
Mixin, not a core-mod-style class overwrite.

## Documentation

Detailed usage guides ship with the mod and appear in the **Guides** viewer
(main menu → Guides). They cover:

| Guide          | Topic                                                      |
|----------------|------------------------------------------------------------|
| Introduction   | Concept, why Warpspace, contributors                       |
| Jumping        | Step-by-step trip procedure, warp flight chambers          |
| Warp Beacons   | Deploying beacons, requirements, tactical notes            |
| Map            | Droppoint and warp-sector marker legend                    |
| Inhibition     | Reach, reactor-size rule, what gets blocked                |
| HUD            | Icon reference for the corner HUD                          |
| Configuration  | Server and client config entries with defaults             |

The guide markdown lives in [`src/main/resources/docs/`](src/main/resources/docs/)
if you want to read it outside the game.

## Building from source

Requires JDK 21, gradle 8+, and a local StarMade dev-build install.

1. Edit `gradle.properties` and set `starmade_root` to your StarMade install
   directory (trailing slash required).
2. Build:
   ```
   ./gradlew jar
   ```
   The resulting jar is copied into `<starmade_root>/mods/`.

### Project layout

```
src/main/java/warpspace/
├── WarpSpace.java        entry point
├── beacon/               warp beacon feature
├── client/               client-side code
│   ├── hud/                HUD pipeline
│   ├── map/                map overlays (+ util/ for custom map drawer)
│   ├── rendering/          WarpSkybox shader
│   └── sounds/             audio
├── core/                 domain logic (WarpManager, WarpJumpManager, ...)
├── listener/             cross-subsystem event listeners
├── manager/              ConfigManager (SimpleConfig), Event/Loop/PacketManager
├── mixin/                SpongePowered Mixin patches
├── network/              packet definitions
├── server/               server-only loops and state refresh
└── util/                 small utilities

src/main/resources/
├── docs/                 in-game guides (markdown + SVG)
├── mod.json              mod descriptor
├── warpspace.mixins.json mixin config
└── resources/            assets (sprites, shaders, sounds, meshes)
```

## Credits

- **IR0NSIGHT** — original author
- **JakeV** — warp thrust
- **Taswin** — map in warp
- **MekTek** — GUI
- **Ithirahad** — VFX & tweaks
- **DarkenWizMan** — SFX
- **TheDerpGamer** — current maintainer

## License

[MIT](LICENSE). Copyright © 2020 IR0NSIGHT.
