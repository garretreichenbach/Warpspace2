package warpspace.network;

import api.network.Packet;
import api.network.PacketReadBuffer;
import api.network.PacketWriteBuffer;
import org.schema.game.common.data.player.PlayerState;
import warpspace.WarpSpace;
import warpspace.client.WarpProcess;
import warpspace.client.hud.HUDCore;

import java.io.IOException;
import java.util.Arrays;

/**
 * Server → client sync of the per-player {@link WarpProcess} value array.
 */
public class PacketHUDUpdate extends Packet {

	/**
	 * Upper bound on the serialized array length. The real array is sized by
	 * {@code WarpProcess.values().length}, currently well under 32. 256 is
	 * a generous ceiling used as a sanity check to reject obviously bogus
	 * packets without trusting the sender's length claim.
	 */
	private static final int MAX_ARRAY_LENGTH = 256;

	/** Value each process has: 1 happening, 0 not happening. */
	private long[] arr;

	public PacketHUDUpdate(long[] processArray) {
		arr = processArray;
	}

	/** Default constructor required by StarLoader. Do not delete. */
	public PacketHUDUpdate() {
	}

	@Override
	public void writePacketData(PacketWriteBuffer buf) throws IOException {
		long[] snapshot = arr == null ? new long[0] : arr;
		buf.writeInt(snapshot.length);
		for (long l : snapshot) {
			buf.writeLong(l);
		}
	}

	@Override
	public void readPacketData(PacketReadBuffer buf) throws IOException {
		int length = buf.readInt();
		if (length < 0 || length > MAX_ARRAY_LENGTH) {
			throw new IOException("PacketHUDUpdate: bogus array length " + length
					+ " (expected 0.." + MAX_ARRAY_LENGTH + ")");
		}
		long[] read = new long[length];
		for (int i = 0; i < length; i++) {
			read[i] = buf.readLong();
		}
		arr = read;
	}

	@Override
	public void processPacketOnClient() {
		// Swallow any exception inside packet processing so a mismatched
		// array length or other client-side hiccup never bubbles up to the
		// network thread (which would kick the client).
		try {
			WarpProcess.update(arr);
			HUDCore.updateHUD();
		} catch (Exception e) {
			WarpSpace.getInstance().logException("PacketHUDUpdate client processing failed (arr len=" + (arr == null ? -1 : arr.length) + ")", e);
		}
	}

	@Override
	public void processPacketOnServer(PlayerState sender) {
		// Client-to-server direction is not used.
	}

	@Override
	public String toString() {
		return "PacketHUDUpdate{arr=" + Arrays.toString(arr) + '}';
	}
}
