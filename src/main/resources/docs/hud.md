# HUD

A compact HUD panel in the bottom-right corner of your screen tells you
which dimension you are in, whether a jump is queued, and whether an
inhibitor is affecting you.

## Layout

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 700 280" width="700" height="280">
  <defs>
    <radialGradient id="pearlGlow" cx="50%" cy="50%" r="50%">
      <stop offset="0%" stop-color="#c68aff" stop-opacity="1"/>
      <stop offset="100%" stop-color="#6b3a8b" stop-opacity="0.2"/>
    </radialGradient>
  </defs>
  <g font-family="sans-serif">

    <!-- Simulated screen -->
    <rect x="10" y="10" width="680" height="260" rx="4" fill="#06101e" stroke="#2a3a5a" stroke-width="1"/>
    <text x="20" y="28" fill="#3a5a8b" font-size="10" font-style="italic">your screen</text>

    <!-- HUD panel in bottom-right corner -->
    <rect x="500" y="120" width="170" height="130" rx="2" fill="#0a1426" stroke="#3a5a8b" stroke-width="1"/>
    <text x="585" y="140" fill="#c8d4ec" font-size="11" text-anchor="middle" font-style="italic">HUD panel</text>

    <!-- Spiral (dimension indicator) -->
    <g transform="translate(540,180)">
      <path d="M 0 -16 A 16 16 0 1 1 -11 11 A 11 11 0 1 1 0 6 A 6 6 0 1 1 -3 0"
            fill="none" stroke="#c8d4ec" stroke-width="2"/>
    </g>
    <text x="540" y="222" fill="#9ab4e0" font-size="9" text-anchor="middle">dimension</text>

    <!-- Arrow (direction) -->
    <g transform="translate(595,180)" fill="#c8d4ec">
      <polygon points="-12,-6 6,0 -12,6 -6,0"/>
    </g>
    <text x="595" y="222" fill="#9ab4e0" font-size="9" text-anchor="middle">dir</text>

    <!-- Arrow to corner -->
    <line x1="490" y1="100" x2="510" y2="120" stroke="#9ab4e0" stroke-width="1"/>
    <text x="480" y="96" fill="#9ab4e0" font-size="11" text-anchor="end">white panel,</text>
    <text x="480" y="110" fill="#9ab4e0" font-size="11" text-anchor="end">bottom-right</text>

    <text x="350" y="60" fill="#e0eaff" font-size="13" text-anchor="middle" font-style="italic">Two icons: left = dimension state, right = direction / jump state</text>
  </g>
</svg>
```

## Icon reference

Each icon can appear in a **neutral** or **blocked** state. Blocked variants
are red and indicate a Warp Inhibitor is preventing travel in that direction.

```svg
<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 660 320" width="660" height="320">
  <defs>
    <radialGradient id="pearlIcon">
      <stop offset="0%" stop-color="#f0d0ff"/>
      <stop offset="100%" stop-color="#6b3a8b"/>
    </radialGradient>
  </defs>
  <g font-family="sans-serif">

    <!-- Column headers -->
    <text x="100" y="30" fill="#c8d4ec" font-size="12" text-anchor="middle" font-style="italic">Neutral</text>
    <text x="250" y="30" fill="#ff9999" font-size="12" text-anchor="middle" font-style="italic">Blocked</text>
    <text x="380" y="30" fill="#e0eaff" font-size="12" text-anchor="start" font-weight="bold">Meaning</text>

    <!-- Row 1: spiral (hollow) = realspace -->
    <g transform="translate(100,70)">
      <path d="M 0 -16 A 16 16 0 1 1 -11 11 A 11 11 0 1 1 0 6 A 6 6 0 1 1 -3 0"
            fill="none" stroke="#c8d4ec" stroke-width="2"/>
    </g>
    <g transform="translate(250,70)">
      <path d="M 0 -16 A 16 16 0 1 1 -11 11 A 11 11 0 1 1 0 6 A 6 6 0 1 1 -3 0"
            fill="none" stroke="#ff4444" stroke-width="2"/>
    </g>
    <text x="380" y="70" fill="#e0eaff" font-size="13">Spiral — you are in realspace</text>
    <text x="380" y="86" fill="#ff9999" font-size="11">red variant: your sector is inhibited</text>

    <!-- Row 2: pearl (filled) = warp -->
    <g transform="translate(100,130)">
      <circle r="12" fill="url(#pearlIcon)" stroke="#c68aff" stroke-width="1.5"/>
    </g>
    <g transform="translate(250,130)">
      <circle r="12" fill="#ff4444" stroke="#7a0000" stroke-width="1.5"/>
    </g>
    <text x="380" y="130" fill="#e0eaff" font-size="13">Pearl — you are in Warpspace</text>
    <text x="380" y="146" fill="#ff9999" font-size="11">red variant: your warp sector is inhibited</text>

    <!-- Row 3: arrow to realspace (up) -->
    <g transform="translate(100,190)" fill="#c8d4ec">
      <polygon points="-6,6 0,-10 6,6 0,2"/>
    </g>
    <g transform="translate(250,190)" fill="#ff4444">
      <polygon points="-6,6 0,-10 6,6 0,2"/>
    </g>
    <text x="380" y="190" fill="#e0eaff" font-size="13">Arrow up — dropping to realspace</text>
    <text x="380" y="206" fill="#ff9999" font-size="11">red variant: corresponding realspace sector is inhibited</text>

    <!-- Row 4: arrow to warp (down) -->
    <g transform="translate(100,250)" fill="#c8d4ec">
      <polygon points="-6,-6 0,10 6,-6 0,-2"/>
    </g>
    <g transform="translate(250,250)" fill="#ff4444">
      <polygon points="-6,-6 0,10 6,-6 0,-2"/>
    </g>
    <text x="380" y="250" fill="#e0eaff" font-size="13">Arrow down — entering Warpspace</text>
    <text x="380" y="266" fill="#ff9999" font-size="11">red variant: corresponding warp sector is inhibited</text>

    <!-- Row 5: blinking jump arrow -->
    <g transform="translate(100,300)" fill="#ffe066">
      <polygon points="-5,5 0,-8 5,5 0,2"/>
    </g>
    <text x="250" y="305" fill="#9ab4e0" font-size="11" text-anchor="middle">—</text>
    <text x="380" y="303" fill="#e0eaff" font-size="13">Yellow arrow (blinks) — jump is charging</text>
  </g>
</svg>
```

## Quick reference table

| Icon                  | Meaning                                           |
|-----------------------|---------------------------------------------------|
| Hollow spiral         | In realspace                                      |
| Filled pearl          | In Warpspace                                      |
| Red spiral / pearl    | Current sector is inhibited                       |
| Arrow (up, white)     | Dropping back to realspace                        |
| Arrow (down, white)   | Entering Warpspace                                |
| Arrow (blinking yellow) | Jump drive charging                             |
| Arrow (red)           | The other-dimension sector is inhibited           |

## Reading the HUD in context

- **Realspace, quiet**: hollow spiral + down arrow (pointing to where you
  would enter warp).
- **Realspace, inhibited**: hollow spiral + **red** down arrow — you can't
  jump to warp right now.
- **In warp, cruising**: filled pearl + up arrow (pointing to where you'd
  drop).
- **In warp, about to drop**: pearl + blinking yellow up arrow.
- **In warp, sector under you is locked**: pearl + **red** up arrow — drop
  would be blocked, but a speeddrop still pierces.
