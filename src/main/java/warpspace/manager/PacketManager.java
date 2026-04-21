package warpspace.manager;

import api.network.packets.PacketUtil;
import warpspace.beacon.BeaconUpdatePacket;
import warpspace.network.PacketHUDUpdate;

/**
 * Central registry for all network packets used by the mod.
 * All {@link api.network.Packet} subclasses must be registered here
 * before they can be sent or received.
 *
 * <p>Note: config sync is handled by {@link api.utils.simpleconfig.SimpleConfigContainer}
 * and does not require a packet registration here.
 */
public final class PacketManager {

    private PacketManager() {
    }

    public static void registerAll() {
        PacketUtil.registerPacket(PacketHUDUpdate.class);
        PacketUtil.registerPacket(BeaconUpdatePacket.class);
    }
}
