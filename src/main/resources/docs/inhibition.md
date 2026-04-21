# Inhibition

Inhibitors block warp travel within a radius. They work in both dimensions —
one inhibitor can deny ships **entering** the sector and **leaving via jump
drive**. Slow speed drops from warpspace are the only way to pierce an active
inhibitor.

## Range and reactor rule

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 640 400" width="640" height="400">
  <g font-family="sans-serif">
    <text x="320" y="28" fill="#e0eaff" font-size="14" font-weight="bold" text-anchor="middle">Inhibitor reach: 3-sector radius</text>

    <!-- 7x7 sector grid (inhibitor at centre, 3-sector reach in all directions) -->
    <g transform="translate(140,50)">
      <rect x="0" y="0" width="350" height="350" fill="#0a1426" stroke="#3a5a8b" stroke-width="1"/>
      <g stroke="#1e3a66" stroke-width="0.5">
        <line x1="50"  y1="0" x2="50"  y2="350"/>
        <line x1="100" y1="0" x2="100" y2="350"/>
        <line x1="150" y1="0" x2="150" y2="350"/>
        <line x1="200" y1="0" x2="200" y2="350"/>
        <line x1="250" y1="0" x2="250" y2="350"/>
        <line x1="300" y1="0" x2="300" y2="350"/>
        <line x1="0" y1="50"  x2="350" y2="50"/>
        <line x1="0" y1="100" x2="350" y2="100"/>
        <line x1="0" y1="150" x2="350" y2="150"/>
        <line x1="0" y1="200" x2="350" y2="200"/>
        <line x1="0" y1="250" x2="350" y2="250"/>
        <line x1="0" y1="300" x2="350" y2="300"/>
      </g>

      <!-- Inhibited region: 3 sectors around centre -->
      <rect x="25" y="25" width="300" height="300" fill="#3a0f0f" fill-opacity="0.35" stroke="#ff6666" stroke-width="1.2" stroke-dasharray="4 3"/>

      <!-- Inhibitor at centre -->
      <g transform="translate(175,175)">
        <circle r="10" fill="#ff4444" stroke="#7a0000" stroke-width="1.5"/>
        <circle r="4"  fill="#ffdd66"/>
      </g>
      <text x="175" y="200" fill="#ff9999" font-size="11" text-anchor="middle">inhibitor</text>

      <!-- Blocked ship (inside range) -->
      <g transform="translate(75,100)">
        <polygon points="-6,-4 6,0 -6,4" fill="#9ab4e0"/>
      </g>
      <text x="75" y="92" fill="#9ab4e0" font-size="10" text-anchor="middle">blocked</text>

      <!-- Allowed ship (outside range) -->
      <g transform="translate(330,340)">
        <polygon points="-6,-4 6,0 -6,4" fill="#7aff7a"/>
      </g>
      <text x="330" y="332" fill="#7aff7a" font-size="10" text-anchor="middle">free to jump</text>

      <!-- Distance label -->
      <line x1="175" y1="175" x2="325" y2="175" stroke="#ffaa66" stroke-width="1" stroke-dasharray="2 2"/>
      <text x="250" y="170" fill="#ffc296" font-size="10" text-anchor="middle">3 sectors</text>
    </g>

    <text x="320" y="390" fill="#c8d4ec" font-size="11" text-anchor="middle" font-style="italic">Reach covers both realspace and the corresponding warp sector.</text>
  </g>
</svg>
```

/// caption
An inhibitor blocks warp jumps inside a 3-sector radius. Ships outside the red region can jump freely; ships inside cannot initiate or complete a warp jump while the inhibitor is active on a loaded entity.
///

## Reactor-size rule

A ship can only be inhibited by fields stronger than its own warp signature.

> An inhibitor can deny warp transit to any ship whose reactor is **up to
> 3× larger** than the inhibitor's. Ships with reactors more than 3× larger
> ignore the inhibitor entirely.

This keeps small patrol frigates from locking down capital-class warp traffic
with cheap inhibitor chambers, while still letting fleet-scale inhibitors
shut down entire engagement zones.

## What gets blocked

| Scenario                                           | Blocked? |
|----------------------------------------------------|----------|
| Entering warp from an inhibited sector             | Yes      |
| Dropping out of warp with jump drive               | Yes      |
| Entering an inhibited sector in warp (passes over) | Yes      |
| **Speeddrop** in warp (slow < 50 m/s for 10s)      | **No** — pierces the inhibitor |
| Sublight travel                                    | No       |

## HUD readout

When an inhibitor affects you, red icons replace the usual blue/white ones in
the corner HUD. See the HUD guide for the full icon legend — in short:

- **Red spiral** — your current sector is inhibited
- **Red arrows** — the corresponding sector in the other dimension is
  inhibited (so dropping or entering warp from here would not work)

## Tactical notes

- Inhibitors only work while on a **loaded** entity. An unpowered or
  unloaded inhibitor station does nothing.
- Combining many small inhibitors does not stack range or power; each checks
  independently.
- Warp Beacons and inhibitors interact: a beacon in an inhibited warp sector
  still pulls droppoints, but arriving ships will find the drop blocked.
