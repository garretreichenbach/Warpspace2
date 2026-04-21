# Warp Beacons

Natural droppoints can be shifted to a more desirable position by deploying a
space station with a **Beacon chamber**.

- The Beacon chamber does **not** consume energy, but it reserves a fraction
  of the reactor's chamber capacity (default 25%, server-configurable).
- Once the beacon addon is activated in the reactor menu, **every ship** that
  would drop to a nearby natural droppoint will instead drop at the station's
  sector.
- The chamber stays active across sector loading/unloading and server
  restarts.

## Requirements

Beacons can only be deployed on **non-homebase** stations with **undamaged**
chambers. Reboot the station with `Y` before activating the chamber to avoid
state issues.
