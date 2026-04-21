# Introduction

WarpSpace replaces StarMade's vanilla "teleport to your waypoint" jump drive
with a **parallel dimension** called **Warpspace**. Instead of vanishing and
reappearing, your ship physically travels - in Warpspace distances are
10x shorter, so long trips take a fraction of the time.

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 760 260" width="760" height="260">
  <defs>
    <marker id="arrow-down" viewBox="0 0 10 10" refX="5" refY="9" markerWidth="7" markerHeight="7" orient="auto">
      <path d="M 0 0 L 5 10 L 10 0 z" fill="#c68aff"/>
    </marker>
    <marker id="arrow-up" viewBox="0 0 10 10" refX="5" refY="1" markerWidth="7" markerHeight="7" orient="auto">
      <path d="M 0 10 L 5 0 L 10 10 z" fill="#7aa2ff"/>
    </marker>
  </defs>
  <g font-family="sans-serif">

    <rect x="20" y="40" width="720" height="70" rx="6" fill="#0f1f3a" stroke="#3a5a8b" stroke-width="1.5"/>
    <text x="32" y="60" fill="#9ab4e0" font-size="12" font-style="italic">Realspace</text>
    <g stroke="#3a5a8b" stroke-width="1">
      <line x1="80"  y1="75" x2="80"  y2="95"/>
      <line x1="146" y1="75" x2="146" y2="95"/>
      <line x1="212" y1="75" x2="212" y2="95"/>
      <line x1="278" y1="75" x2="278" y2="95"/>
      <line x1="344" y1="75" x2="344" y2="95"/>
      <line x1="410" y1="75" x2="410" y2="95"/>
      <line x1="476" y1="75" x2="476" y2="95"/>
      <line x1="542" y1="75" x2="542" y2="95"/>
      <line x1="608" y1="75" x2="608" y2="95"/>
      <line x1="674" y1="75" x2="674" y2="95"/>
    </g>
    <circle cx="80"  cy="85" r="7" fill="#7aa2ff"/>
    <circle cx="674" cy="85" r="7" fill="#7aa2ff"/>
    <text x="80"  y="130" fill="#e0eaff" font-size="12" text-anchor="middle">A</text>
    <text x="674" y="130" fill="#e0eaff" font-size="12" text-anchor="middle">B</text>
    <text x="377" y="128" fill="#9ab4e0" font-size="11" text-anchor="middle">10 realspace sectors</text>

    <rect x="310" y="160" width="132" height="70" rx="6" fill="#1a0f2a" stroke="#6b3a8b" stroke-width="1.5"/>
    <text x="322" y="180" fill="#c0a0e8" font-size="12" font-style="italic">Warpspace</text>
    <g stroke="#6b3a8b" stroke-width="1">
      <line x1="320" y1="195" x2="320" y2="215"/>
      <line x1="332" y1="195" x2="332" y2="215"/>
      <line x1="344" y1="195" x2="344" y2="215"/>
      <line x1="356" y1="195" x2="356" y2="215"/>
      <line x1="368" y1="195" x2="368" y2="215"/>
      <line x1="380" y1="195" x2="380" y2="215"/>
      <line x1="392" y1="195" x2="392" y2="215"/>
      <line x1="404" y1="195" x2="404" y2="215"/>
      <line x1="416" y1="195" x2="416" y2="215"/>
      <line x1="428" y1="195" x2="428" y2="215"/>
    </g>
    <circle cx="320" cy="205" r="5" fill="#c68aff"/>
    <circle cx="428" cy="205" r="5" fill="#c68aff"/>
    <text x="376" y="250" fill="#c0a0e8" font-size="11" text-anchor="middle">same trip, 1 warp sector</text>

    <line x1="80"  y1="100" x2="320" y2="198" stroke="#c68aff" stroke-width="1.8" marker-end="url(#arrow-down)"/>
    <line x1="428" y1="198" x2="674" y2="100" stroke="#7aa2ff" stroke-width="1.8" marker-end="url(#arrow-up)"/>

    <text x="380" y="28" fill="#e0eaff" font-size="14" text-anchor="middle" font-weight="bold">Two dimensions, same galaxy</text>
  </g>
</svg>
```

/// caption
Warpspace is a parallel, compressed copy of realspace. Every 10 realspace sectors collapses into 1 warp sector, so long-distance travel is much faster — but the trip is still physical, not instantaneous.
///

## Why it matters

- **Predictable fast travel.** You can be followed, intercepted, or ambushed
  mid-trip. Flash raids that rely on a one-way escape jump no longer work.
- **A shared dimension.** Every ship travelling FTL is in the same warpspace
  at the same time. Meeting other players in transit is expected, not a bug.
- **Defensible territory.** Because trips take real time, defenders can
  respond to incoming threats instead of watching attackers teleport in.

> If you are new, start with the **Jumping** guide to get your first warp
> trip under your belt, then come back for the finer details on beacons,
> inhibitors, and HUD readouts.

## Contributors

- **IR0NSIGHT** — original author
- **JakeV** — warp thrust
- **Taswin** — map in warp
- **MekTek** — GUI
- **Ithirahad** — VFX & tweaks
- **DarkenWizMan** — SFX
- **TheDerpGamer** — current maintainer
