package warpspace.beacon;

import api.network.Packet;
import api.network.PacketReadBuffer;
import api.network.PacketWriteBuffer;
import api.network.packets.PacketUtil;
import org.schema.game.common.data.player.PlayerState;
import org.schema.game.server.data.GameServerState;
import warpspace.WarpSpace;

import java.io.IOException;

public class BeaconUpdatePacket extends Packet {
    @Override
    public void readPacketData(PacketReadBuffer packetReadBuffer) throws IOException {
	    BeaconManager client = WarpSpace.getInstance().getBeaconManagerClient();
        if (client == null)
            return;
        client.onDeserialize(packetReadBuffer);
    }

    @Override
    public void writePacketData(PacketWriteBuffer packetWriteBuffer) throws IOException {
	    WarpSpace.getInstance().getBeaconManagerServer().onSerialize(packetWriteBuffer);
    }

    @Override
    public void processPacketOnClient() {
        //handled in read data
    }

    @Override
    public void processPacketOnServer(PlayerState playerState) {
    }

    public void sendToAll() {
        for (PlayerState playerState: GameServerState.instance.getPlayerStatesByName().values()) {
            PacketUtil.sendPacket(playerState,this);
        }
    }
}
