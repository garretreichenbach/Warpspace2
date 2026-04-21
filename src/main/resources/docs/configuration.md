# Configuration

WarpSpace is highly configurable. Settings live in the StarMade install folder:

```
<starmade-install>/moddata/warpspace/warpspaceConfig.yaml
<starmade-install>/moddata/warpspace/warpspaceClientConfig.yaml
```

## Server config (`warpspaceConfig.yaml`)

Server-authoritative entries — automatically synced to clients on login.

Covers gameplay and balance: warp/realspace scale ratio, drop-speed threshold,
jumpdrive delay, warp-flight chamber multipliers, beacon chamber capacity,
killswitches for astronaut and speed drop.

## Client config (`warpspaceClientConfig.yaml`)

Per-user client preferences only — never overridden by the server.

Covers: warp shader toggle, sound-effect toggle, map droppoint draw range.

## Resetting to defaults

Delete the line or the whole config file; it will be regenerated with defaults
on the next start.
