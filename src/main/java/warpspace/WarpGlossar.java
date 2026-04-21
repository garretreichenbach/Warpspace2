package warpspace;

import glossar.GlossarCategory;
import glossar.GlossarEntry;

/**
 * Holds the static glossary content for the mod.
 *
 * <p>TODO: This entire class will be replaced when the mod migrates to
 * StarMade's new in-game guide system (markdown + svg). See the "Migrate
 * glossary to StarMade in-game guide" task.
 */
public final class WarpGlossar {

    private static final String INTRODUCTION_TEXT =
            "WarpSpace changes the jumping mechanic. Instead of being teleported to your waypoint or in the "
            + "direction of the waypoint, instead you enter a parallel dimension that is a scaled down version "
            + "of realspace: the Warp. Here you can travel using your ship's engines, just as in realspace, but "
            + "distances are ten times shorter. \n"
            + "This means that you can follow others/be followed when you are travelling faster than light. "
            + "The core feature is that fast-travel becomes predictable.\n"
            + " This renders flash raid-attacks, which rely on jumping away to hide, useless and greatly "
            + "improves the ability to defend your territory.\n"
            + "Since the Warp is shared by everyone, its not unlikely to meet other players in it.\n\n\n"
            + "Contributors:\n"
            + "IR0NSIGHT (author)\n"
            + "JakeV (warpthrust)\n"
            + "Taswin (Map in Warp)\n"
            + "MekTek (GUI)\n"
            + "Ithirahad (VFX & tweaks)\n"
            + "DarkenWizMan (SFX)";

    private static final String JUMPING_TEXT =
            "Set your desired destination as your navigation waypoint ('N'). Activate your jumpdrive. "
            + "After a couple seconds you will switch dimensions and enter the Warp. "
            + "The Warp is a parallel dimension where distances are 10 times shorter. Follow your waypoint "
            + "while you are in warp, until you reach it. You will see a notification 'droppoint' reached. "
            + "Activate your jumpdrive again or slow down below 50 m/s for more than 10 seconds, to drop out "
            + "of warp. You will re-enter realspace at the corresponding droppoint. This is your waypoint "
            + "rounded to 10. After dropping, fly the remaining distance to your waypoint in realspace.\n"
            + "If you want to go faster in Warp, try installing Warp Speed chambers (a replacement for vanilla "
            + "Jump Distance) to increase your maximum flight speed within Warp Space.";

    private static final String BEACON_TEXT =
            "The natural drop-points can be shifted to a more desirable position, by deploying a space station "
            + "with a Beacon chamber. The chamber does not use energy, but costs 50% of the reactor's chamber "
            + "capacity. Once the beacon addon is activated in the reactor menu, every ship will drop out at "
            + "the stations sector, instead of the nearest natural drop point. The chamber stays active across "
            + "loading/unloading the sector and server restarts.\n"
            + " Beacons can only be deployed on non-homebase stations with undamaged chambers. It is "
            + "recommended to reboot the station with 'y' before activating the chamber.";

    private static final String MAP_TEXT =
            "While in warp, a scaled down version of the universe is visible on the map. \n"
            + "In realspace, the droppoints are marked with small, blue spirals. Droppoints, that were "
            + "shifted through the use of warpbeacons, are marked by the same symbol inside a box.";

    private static final String INHIBITION_TEXT =
            "Inhibition has been reworked and enhanced. Your HUD will tell you if an inhibitor is impacting "
            + "you. It will show red symbols for inhibitors denying you leaving your current dimension or "
            + "entering the other one. An active inhibitor will prevent ships from entering your sector and "
            + "leaving your sector by using the jumpdrive. Note that slowing down and 'speeddropping' in warp "
            + "pierces the inhibitor. Ships/Stations that field an inhibitor can deny warping to any ships "
            + "that have up to 3x larger reactors within a 3 sector radius. Inhibitors only work while on a "
            + "loaded entity.";

    private static final String HUD_TEXT =
            "In your bottom right corner of the screen, you will see a white HUD element. The HUD will give "
            + "you information about:\n"
            + "- The dimension you are in:\n"
            + "    - hollow spiral = realspace\n"
            + "    - filled spiral spinning fast = Warp\n"
            + "- If you are currently jumping or dropping: small yellow arrows blinking\n"
            + "- If you are being inhibited:\n"
            + "    - Red spiral: your sector is inhibited.\n"
            + "    - Red arrows: the sector in the other dimension is inhibited.";

    private static final String CONFIGURATION_TEXT =
            "Warpspace is highly configurable. Check starmade-install-folder/moddata/warpspace for the "
            + "warpspaceConfig file.\n Game mechanics, Sound effects, Visual effects and more can be "
            + "configure there.";

    private WarpGlossar() {
    }

    public static GlossarCategory buildCategory() {
        GlossarCategory cat = new GlossarCategory("WarpSpace");
        cat.addEntry(new GlossarEntry("Introduction", INTRODUCTION_TEXT));
        cat.addEntry(new GlossarEntry("Jumping", JUMPING_TEXT));
        cat.addEntry(new GlossarEntry("Warp Beacon", BEACON_TEXT));
        cat.addEntry(new GlossarEntry("Map", MAP_TEXT));
        cat.addEntry(new GlossarEntry("Inhibition", INHIBITION_TEXT));
        cat.addEntry(new GlossarEntry("HUD", HUD_TEXT));
        cat.addEntry(new GlossarEntry("Configuration", CONFIGURATION_TEXT));
        return cat;
    }
}
