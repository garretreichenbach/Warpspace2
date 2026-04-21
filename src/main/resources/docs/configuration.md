# Configuration

WarpSpace reads from two YAML config files in your StarMade install folder:

```
<starmade-install>/moddata/warpspace/warpspaceConfig.yaml
<starmade-install>/moddata/warpspace/warpspaceClientConfig.yaml
```

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 720 200" width="720" height="200">
  <defs>
    <marker id="sync" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto">
      <path d="M 0 0 L 10 5 L 0 10 z" fill="#7aa2ff"/>
    </marker>
  </defs>
  <g font-family="sans-serif">
    <text x="360" y="28" fill="#e0eaff" font-size="14" text-anchor="middle" font-weight="bold">Two configs, two scopes</text>

    <!-- Server config -->
    <rect x="40" y="60" width="260" height="110" rx="6" fill="#0f1f3a" stroke="#7aa2ff" stroke-width="1.5"/>
    <text x="170" y="86" fill="#e0eaff" font-size="13" font-weight="bold" text-anchor="middle">warpspaceConfig.yaml</text>
    <text x="170" y="106" fill="#9ab4e0" font-size="11" text-anchor="middle">server-authoritative</text>
    <text x="170" y="124" fill="#9ab4e0" font-size="11" text-anchor="middle">synced to clients on login</text>
    <text x="170" y="148" fill="#c8d4ec" font-size="11" text-anchor="middle">gameplay &amp; balance</text>

    <!-- Sync arrow -->
    <line x1="305" y1="115" x2="415" y2="115" stroke="#7aa2ff" stroke-width="2" stroke-dasharray="4 3" marker-end="url(#sync)"/>
    <text x="360" y="108" fill="#c8d4ec" font-size="11" text-anchor="middle">auto-sync</text>

    <!-- Client config -->
    <rect x="420" y="60" width="260" height="110" rx="6" fill="#1a0f2a" stroke="#c68aff" stroke-width="1.5"/>
    <text x="550" y="86" fill="#f0e0ff" font-size="13" font-weight="bold" text-anchor="middle">warpspaceClientConfig.yaml</text>
    <text x="550" y="106" fill="#d0b4e8" font-size="11" text-anchor="middle">client-local</text>
    <text x="550" y="124" fill="#d0b4e8" font-size="11" text-anchor="middle">never overridden by server</text>
    <text x="550" y="148" fill="#f0e0ff" font-size="11" text-anchor="middle">per-user preferences</text>
  </g>
</svg>
```

/// caption
The server config is server-owned; when clients log in they receive a copy and use the server's values. The client config is yours alone — the server never writes to it.
///

## Server config (`warpspaceConfig.yaml`)

Gameplay and balance. Edits take effect on the server and propagate to
connected clients.

### Killswitches

| Key                                    | Default | Description                                                      |
|----------------------------------------|---------|------------------------------------------------------------------|
| `kill_switch_astronaut_drop`           | `true`  | Suppresses the auto-drop that otherwise applies to astronauts.   |
| `kill_switch_speed_drop`               | `true`  | Suppresses the slow-speed auto-drop check loop entirely.         |

### Warp geometry

| Key                                    | Default  | Description                                                               |
|----------------------------------------|----------|---------------------------------------------------------------------------|
| `warp_to_rsp_ratio`                    | `10.0`   | Warp-to-realspace scale. 1 warp sector = N realspace sectors.             |
| `droppoint_random_offset_sectors`      | `2.25`   | Random shift applied to natural droppoints in each axis (deterministic).  |
| `seconds_warpjump_delay`               | `9.5`    | Jump drive charge time, in seconds. Minimum 9.5s.                         |
| `seconds_until_speeddrop`              | `30.0`   | Continuous seconds below the minimum warp speed before a ship drops out.  |
| `minimum_warp_speed`                   | `50.0`   | Required flight speed in m/s to stay in warp.                             |

### Warp flight chambers

| Key                                          | Default |
|----------------------------------------------|---------|
| `warp_speed_no_chamber_multiplier`           | `1.0`   |
| `warp_speed_chamber_lvl_1_multiplier`        | `1.3`   |
| `warp_speed_chamber_lvl_2_multiplier`        | `1.6`   |
| `warp_speed_chamber_lvl_3_multiplier`        | `2.0`   |

### Warp beacons

| Key                                    | Default | Description                                                      |
|----------------------------------------|---------|------------------------------------------------------------------|
| `warp_beacon_chamber_percent`          | `0.25`  | Fraction of reactor chamber capacity consumed by the beacon.     |
| `warp_beacon_disable_on_homebase`      | `true`  | Disallow beacons on homebase stations.                           |

## Client config (`warpspaceClientConfig.yaml`)

Client-side preferences only. Per-user, never overridden by the server.

| Key                              | Default | Description                                                    |
|----------------------------------|---------|----------------------------------------------------------------|
| `vfx_use_warp_shader`            | `true`  | Enable the in-warp sky shader. Disable if GPU struggles.       |
| `sfx_effects_enable`             | `true`  | Enable warp sound effects (boom, charge-up, etc).              |
| `map_draw_droppoints_range`      | `1`     | How many warp sectors around you to draw droppoint markers for.|

## Resetting to defaults

Delete the config file (or just the specific line) and restart; the mod will
regenerate it with defaults on next load. Comments above each entry in the
generated file describe its purpose.

> Default values listed here match the current release. Server operators
> commonly tune warp ratio, chamber multipliers, and beacon cost to suit
> their server's play-style; consider checking with your server's admin if a
> value seems unexpected.
