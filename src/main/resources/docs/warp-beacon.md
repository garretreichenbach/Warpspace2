# Warp Beacons

Natural droppoints sit on a coarse grid — one every 10 realspace sectors in
each axis by default. A **Warp Beacon** overrides that for its surrounding
region, pulling incoming traffic to the beacon's own sector instead of the
nearest natural droppoint.

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 760 360" width="760" height="360">
  <defs>
    <marker id="pull" viewBox="0 0 10 10" refX="9" refY="5" markerWidth="8" markerHeight="8" orient="auto">
      <path d="M 0 0 L 10 5 L 0 10 z" fill="#ffd27a"/>
    </marker>
  </defs>
  <g font-family="sans-serif">

    <!-- Left: natural droppoints -->
    <text x="180" y="30" fill="#e0eaff" font-size="14" font-weight="bold" text-anchor="middle">Without beacon</text>
    <g transform="translate(40,50)">
      <rect x="0" y="0" width="280" height="280" fill="#0a1426" stroke="#3a5a8b" stroke-width="1"/>
      <g stroke="#1e3a66" stroke-width="0.5">
        <line x1="56"  y1="0" x2="56"  y2="280"/><line x1="112" y1="0" x2="112" y2="280"/>
        <line x1="168" y1="0" x2="168" y2="280"/><line x1="224" y1="0" x2="224" y2="280"/>
        <line x1="0" y1="56"  x2="280" y2="56"/> <line x1="0" y1="112" x2="280" y2="112"/>
        <line x1="0" y1="168" x2="280" y2="168"/><line x1="0" y1="224" x2="280" y2="224"/>
      </g>
      <!-- Natural droppoints on the grid -->
      <g fill="#7aa2ff">
        <circle cx="28"  cy="28"  r="4"/><circle cx="84"  cy="28"  r="4"/><circle cx="140" cy="28"  r="4"/><circle cx="196" cy="28"  r="4"/><circle cx="252" cy="28"  r="4"/>
        <circle cx="28"  cy="84"  r="4"/><circle cx="84"  cy="84"  r="4"/><circle cx="140" cy="84"  r="4"/><circle cx="196" cy="84"  r="4"/><circle cx="252" cy="84"  r="4"/>
        <circle cx="28"  cy="140" r="4"/><circle cx="84"  cy="140" r="4"/><circle cx="140" cy="140" r="4"/><circle cx="196" cy="140" r="4"/><circle cx="252" cy="140" r="4"/>
        <circle cx="28"  cy="196" r="4"/><circle cx="84"  cy="196" r="4"/><circle cx="140" cy="196" r="4"/><circle cx="196" cy="196" r="4"/><circle cx="252" cy="196" r="4"/>
        <circle cx="28"  cy="252" r="4"/><circle cx="84"  cy="252" r="4"/><circle cx="140" cy="252" r="4"/><circle cx="196" cy="252" r="4"/><circle cx="252" cy="252" r="4"/>
      </g>
      <text x="140" y="305" fill="#9ab4e0" font-size="11" text-anchor="middle">ships drop at nearest grid point</text>
    </g>

    <!-- Right: beacon active -->
    <text x="580" y="30" fill="#e0eaff" font-size="14" font-weight="bold" text-anchor="middle">With active beacon</text>
    <g transform="translate(440,50)">
      <rect x="0" y="0" width="280" height="280" fill="#0a1426" stroke="#3a5a8b" stroke-width="1"/>
      <g stroke="#1e3a66" stroke-width="0.5">
        <line x1="56"  y1="0" x2="56"  y2="280"/><line x1="112" y1="0" x2="112" y2="280"/>
        <line x1="168" y1="0" x2="168" y2="280"/><line x1="224" y1="0" x2="224" y2="280"/>
        <line x1="0" y1="56"  x2="280" y2="56"/> <line x1="0" y1="112" x2="280" y2="112"/>
        <line x1="0" y1="168" x2="280" y2="168"/><line x1="0" y1="224" x2="280" y2="224"/>
      </g>
      <!-- Beacon glow -->
      <circle cx="155" cy="155" r="72" fill="#3a1f6b" fill-opacity="0.3" stroke="#ffd27a" stroke-width="1" stroke-dasharray="3 3"/>

      <!-- Pull arrows from surrounding grid points toward beacon -->
      <g fill="#7aa2ff" fill-opacity="0.35">
        <circle cx="140" cy="84"  r="4"/><circle cx="196" cy="84"  r="4"/><circle cx="84"  cy="140" r="4"/><circle cx="140" cy="140" r="4"/><circle cx="196" cy="140" r="4"/><circle cx="84"  cy="196" r="4"/><circle cx="140" cy="196" r="4"/><circle cx="196" cy="196" r="4"/>
      </g>
      <g stroke="#ffd27a" stroke-width="1.2">
        <line x1="140" y1="84"  x2="152" y2="147" marker-end="url(#pull)"/>
        <line x1="196" y1="84"  x2="158" y2="147" marker-end="url(#pull)"/>
        <line x1="84"  y1="140" x2="148" y2="152" marker-end="url(#pull)"/>
        <line x1="196" y1="140" x2="162" y2="152" marker-end="url(#pull)"/>
        <line x1="84"  y1="196" x2="148" y2="162" marker-end="url(#pull)"/>
        <line x1="140" y1="196" x2="152" y2="164" marker-end="url(#pull)"/>
        <line x1="196" y1="196" x2="162" y2="164" marker-end="url(#pull)"/>
      </g>

      <!-- Beacon station icon -->
      <rect x="147" y="147" width="16" height="16" fill="#ffd27a" stroke="#a06b00" stroke-width="1"/>
      <text x="155" y="132" fill="#ffd27a" font-size="11" text-anchor="middle">beacon</text>
      <text x="140" y="305" fill="#9ab4e0" font-size="11" text-anchor="middle">all ships drop at the beacon's sector</text>
    </g>
  </g>
</svg>
```

/// caption
Without a beacon, warp trips end at the nearest natural droppoint on the 10-sector grid. When you activate a beacon on a station, nearby droppoints are reassigned to your station's sector — so anyone warping into the area lands where you want them.
///

## How to deploy one

1. Build a non-homebase space station.
2. Install a **Warp Beacon chamber** on its reactor tree.
3. Open the reactor menu and activate the beacon addon.

The chamber persists across sector loading/unloading and server restarts. You
do not need to keep the station loaded for the beacon to remain active.

## Requirements

| Requirement          | Rule                                         |
|----------------------|----------------------------------------------|
| Entity type          | Space station only                           |
| Homebase             | **Not allowed** (by default — configurable)  |
| Chamber health       | Must be undamaged                            |
| Chamber cost         | 25% of reactor chamber capacity (default)    |
| Power draw           | None                                         |

> **Tip:** reboot the station with `Y` before activating the chamber to clear
> any stale chamber state.

## Tactical notes

- Beacons are **publicly routed**: anyone warping to your region will land at
  your sector, including hostiles. Build defences accordingly.
- Stacking multiple beacons in the same warp sector picks the highest-strength
  one; ties are broken deterministically per position.
- Disable the beacon (re-activate the chamber) to revert the droppoint to its
  natural grid position.
