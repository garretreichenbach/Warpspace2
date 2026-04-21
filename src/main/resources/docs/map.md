# Map

The galaxy map (`M`) picks up extra WarpSpace overlays so you can plan jumps
and spot beacons at a glance. Both dimensions share the same sector
coordinates, so the map behaves the same whether you are in realspace or
Warpspace — only the markers change.

## Marker legend

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 660 300" width="660" height="300">
  <g font-family="sans-serif">

    <text x="30" y="30" fill="#e0eaff" font-size="14" font-weight="bold">Realspace markers</text>

    <!-- Natural droppoint: blue spiral -->
    <rect x="30" y="50" width="60" height="60" fill="#0a1426" stroke="#3a5a8b" stroke-width="1"/>
    <g transform="translate(60,80)">
      <path d="M 0 -10 A 10 10 0 1 1 -7 7 A 7 7 0 1 1 0 4 A 4 4 0 1 1 -2 0"
            fill="none" stroke="#7aa2ff" stroke-width="1.8"/>
    </g>
    <text x="110" y="75" fill="#e0eaff" font-size="13">Natural droppoint</text>
    <text x="110" y="93" fill="#9ab4e0" font-size="11">where ships drop back into realspace</text>

    <!-- Shifted droppoint: boxed spiral -->
    <rect x="30" y="130" width="60" height="60" fill="#0a1426" stroke="#3a5a8b" stroke-width="1"/>
    <rect x="45" y="145" width="30" height="30" fill="none" stroke="#ffd27a" stroke-width="1.4"/>
    <g transform="translate(60,160)">
      <path d="M 0 -8 A 8 8 0 1 1 -5.5 5.5 A 5.5 5.5 0 1 1 0 3 A 3 3 0 1 1 -1.5 0"
            fill="none" stroke="#7aa2ff" stroke-width="1.6"/>
    </g>
    <text x="110" y="155" fill="#e0eaff" font-size="13">Shifted droppoint (beacon)</text>
    <text x="110" y="173" fill="#9ab4e0" font-size="11">a Warp Beacon is redirecting traffic to its sector</text>

    <text x="360" y="30" fill="#e0eaff" font-size="14" font-weight="bold">Warpspace markers</text>

    <!-- Current sector cube -->
    <rect x="360" y="50" width="60" height="60" fill="#0a1426" stroke="#6b3a8b" stroke-width="1"/>
    <g transform="translate(390,80)" fill="none" stroke="#00e5ff" stroke-width="1.4">
      <polygon points="-14,-10 4,-14 18,-6 18,12 0,16 -14,12"/>
      <line x1="-14" y1="-10" x2="0" y2="-6"/>
      <line x1="0" y1="-6" x2="18" y2="-6"/>
      <line x1="0" y1="-6" x2="0" y2="16"/>
      <line x1="4" y1="-14" x2="0" y2="-6"/>
    </g>
    <text x="440" y="75" fill="#e0eaff" font-size="13">Current warp sector (cyan cube)</text>
    <text x="440" y="93" fill="#9ab4e0" font-size="11">drawn at the realspace position of your warp sector</text>

    <!-- Scaled galaxy dots -->
    <rect x="360" y="130" width="60" height="60" fill="#0a1426" stroke="#6b3a8b" stroke-width="1"/>
    <g transform="translate(390,160)">
      <circle cx="-12" cy="-8" r="1.2" fill="#ffe6a0"/>
      <circle cx="-4"  cy="-12" r="1" fill="#ffd27a"/>
      <circle cx="6"   cy="-4" r="1.4" fill="#fff8dc"/>
      <circle cx="-8"  cy="4" r="1" fill="#ffd27a"/>
      <circle cx="12"  cy="8" r="1.3" fill="#ffe6a0"/>
      <circle cx="-14" cy="12" r="1" fill="#ffd27a"/>
      <circle cx="2"   cy="10" r="1.2" fill="#fff8dc"/>
    </g>
    <text x="440" y="155" fill="#e0eaff" font-size="13">Scaled starfield</text>
    <text x="440" y="173" fill="#9ab4e0" font-size="11">a 10x-smaller view of the realspace galaxy</text>

    <text x="330" y="245" fill="#c8d4ec" font-size="12" text-anchor="middle" font-style="italic">Open the map with M. Droppoint markers auto-refresh every 2 seconds.</text>
  </g>
</svg>
```

## What you see, when

| Dimension  | Visible markers                                               |
|------------|---------------------------------------------------------------|
| Realspace  | Natural droppoints (blue spirals), shifted droppoints (boxed) |
| Warpspace  | Scaled galaxy starfield, cyan cube marking your warp sector   |

## Controls

- The **droppoint draw range** (how many warp sectors around your current
  sector get markers) is configurable per client. Default is 1 (only your
  immediate warp sector's realspace equivalents).
- Markers refresh when you change sector, when a beacon toggles, or every 2
  seconds as a safety net.
