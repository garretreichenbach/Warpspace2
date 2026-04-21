package warpspace.client.sounds;

import warpspace.WarpMain;
import warpspace.client.WarpProcess;
import warpspace.client.WarpProcessListener;
import warpspace.manager.ConfigManager;
import warpspace.util.TimedRunnable;

public class JumpdriveSounds extends WarpProcessListener {
	public static final String QUEUE_ID = "Engine";
    @Override
    public void onValueChange(WarpProcess c) {
        super.onValueChange(c);
        switch (c) {
            case JUMPDROP:
                if (!c.wasTrue() && c.isTrue())
                    queue(SoundQueueManager.SoundEntry.warp_boom);
                break;
            case JUMPEXIT: //fallthrough
            case JUMPENTRY:
                if (!c.wasTrue() && c.isTrue()) {
                    int delay = (int) (ConfigManager.getSecondsWarpjumpDelay() * 1000 - 9300);
                    new TimedRunnable(delay, WarpMain.instance, 1){
                        @Override
                        public void onRun() {
                            queue(SoundQueueManager.SoundEntry.drive_charge_up);
                            queue(SoundQueueManager.SoundEntry.warp_boom);
                        }
                    };
                }
                break;
        }

    }
    public void queue(SoundQueueManager.SoundEntry e) {
        if (!ConfigManager.isSfxEffectsEnabled()) {
            return;
        }
        SoundQueueManager.instance.queueSound(
                new SoundQueueManager.SoundInstance(e, (float) ConfigManager.getSfxEffectsAddDb(), 1),
		        QUEUE_ID);
    }
}
