# Jumping

Warp travel happens in two legs: a fast transit through the Warp dimension,
followed by a short sublight hop from the droppoint to your actual waypoint.

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 780 260" width="780" height="260">
  <defs>
    <marker id="arrow-rsp" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto-start-reverse">
      <path d="M 0 0 L 10 5 L 0 10 z" fill="#7aa2ff"/>
    </marker>
    <marker id="arrow-warp" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto-start-reverse">
      <path d="M 0 0 L 10 5 L 0 10 z" fill="#c68aff"/>
    </marker>
  </defs>
  <g font-family="sans-serif" font-size="13" text-anchor="middle">
    <text x="20" y="28" fill="#9ab4e0" font-size="11" text-anchor="start" font-style="italic">Realspace</text>
    <line x1="20" y1="70" x2="760" y2="70" stroke="#3a5a8b" stroke-width="1" stroke-dasharray="3 3"/>
    <text x="20" y="220" fill="#c0a0e8" font-size="11" text-anchor="start" font-style="italic">Warpspace (10x shorter)</text>
    <line x1="20" y1="190" x2="760" y2="190" stroke="#6b3a8b" stroke-width="1" stroke-dasharray="3 3"/>

    <circle cx="80" cy="70" r="9" fill="#7aa2ff" stroke="#1a3a6b" stroke-width="2"/>
    <text x="80" y="52" fill="#e0eaff">Origin</text>

    <line x1="80" y1="79" x2="170" y2="182" stroke="#7aa2ff" stroke-width="2" marker-end="url(#arrow-rsp)"/>
    <text x="95" y="130" fill="#c8d4ec" font-size="11" text-anchor="start">activate jumpdrive</text>
    <text x="95" y="146" fill="#c8d4ec" font-size="11" text-anchor="start">(9.5s charge)</text>

    <circle cx="170" cy="190" r="6" fill="#c68aff" stroke="#3a1f6b" stroke-width="2"/>

    <line x1="176" y1="190" x2="524" y2="190" stroke="#c68aff" stroke-width="2.5" marker-end="url(#arrow-warp)"/>
    <text x="350" y="182" fill="#f0e0ff" font-size="12">warp transit</text>
    <text x="350" y="228" fill="#c0a0e8" font-size="11">stay above 50 m/s or you drop</text>

    <circle cx="530" cy="190" r="6" fill="#c68aff" stroke="#3a1f6b" stroke-width="2"/>

    <line x1="530" y1="181" x2="620" y2="79" stroke="#7aa2ff" stroke-width="2" marker-end="url(#arrow-rsp)"/>
    <text x="600" y="130" fill="#c8d4ec" font-size="11" text-anchor="end">activate jumpdrive</text>
    <text x="600" y="146" fill="#c8d4ec" font-size="11" text-anchor="end">or speeddrop</text>

    <circle cx="620" cy="70" r="9" fill="#7aa2ff" stroke="#1a3a6b" stroke-width="2"/>
    <text x="620" y="52" fill="#e0eaff">Droppoint</text>

    <line x1="629" y1="70" x2="712" y2="70" stroke="#7aa2ff" stroke-width="1.5" stroke-dasharray="4 2" marker-end="url(#arrow-rsp)"/>
    <text x="670" y="60" fill="#c8d4ec" font-size="11">sublight</text>

    <circle cx="722" cy="70" r="9" fill="#7aa2ff" stroke="#1a3a6b" stroke-width="2"/>
    <text x="722" y="52" fill="#e0eaff">Waypoint</text>
  </g>
</svg>
```

/// caption
A single warp trip: drop down into warpspace, travel 10x faster, pop back up at the nearest drop point, and fly the remainder at sublight.
///

## Procedure

1. Set a navigation **waypoint** with `N`.
2. Activate your **jumpdrive**. After the charge (default 9.5 s) you switch
   dimensions and appear in the Warp.
3. **Follow your waypoint** through the Warp. When you reach the warp sector
   that corresponds to your realspace waypoint, you will see the message
   *"dropout point reached"*.
4. **Drop back out** by either:
   - activating your jumpdrive again, or
   - slowing below 50 m/s for 10 continuous seconds (**speeddrop**).
5. You re-enter realspace at the **droppoint** — your waypoint rounded to the
   configured interval (10 sectors by default).
6. Fly the remaining distance at sublight to reach your actual waypoint.

## Warp speed chambers

Max flight speed inside warpspace is multiplied by an installed **Warp Flight
Chamber** (replaces vanilla Jump Distance). Higher-level chambers give larger
multipliers.

| Chamber               | Warp speed multiplier |
|-----------------------|-----------------------|
| No chamber            | 1.0x                  |
| Warp Flight Level 1   | 1.3x                  |
| Warp Flight Level 2   | 1.6x                  |
| Warp Flight Level 3   | 2.0x                  |

> Defaults shown. Actual multipliers can be tuned per-server — see the Configuration guide.

## Troubleshooting

**"Ship is already jumping"** — you already have a warp entry queued. Let the
current charge finish or cancel it.

**Dropped out of warp unexpectedly** — usually means you slowed below 50 m/s
for too long (speed drop), or a Warp Inhibitor forced you out. Check the HUD
for red icons.

**Jump drive cycles but nothing happens** — your sector, or the target warp
sector, is inhibited. See the Inhibition guide.

**Arrived at the drop point but the waypoint is still far away** — that's
expected: drop points are spaced on a coarse grid. Fly the last leg at
sublight.

**Need to go faster in warp** — install a higher-level Warp Flight Chamber,
or ask your server admin to adjust the multipliers.
