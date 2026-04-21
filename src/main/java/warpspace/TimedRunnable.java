package warpspace;

import api.mod.StarMod;
import api.utils.StarRunnable;

/**
 * drawer that changes HUD stuff in the HUDIndicator overlay before every frame is drawn.
 * overwrite onRun() for your custom methods to run timed.
 */
public class TimedRunnable {
	private final int timeout;
	StarRunnable runner;
	private int runs;

	/**
	 * will create a runnable that runs once every x millis.
	 *
	 * @param timeout   in millis
	 * @param mod       the mod duh
	 * @param runAmount will run x times before autodestruct. -1 for forever
	 */
	public TimedRunnable(int timeout, StarMod mod, int runAmount) {
		this.timeout = timeout;
		runs = runAmount;
		runner = new StarRunnable() {
			private long lastRun = System.currentTimeMillis();
			private long nextRun = lastRun + timeout;

			@Override
			public void run() {
				if(System.currentTimeMillis() > nextRun) {
					lastRun = System.currentTimeMillis();
					nextRun = lastRun + timeout;
					onRun();
					if(canStop()) {
						runs--;
						if(runs <= 0) cancel();
					}

				}
			}
		};
		runner.runTimer(mod, 1);
	}

	private boolean canStop() {
		return (runs >= 0);
	}

	public void doStop() {
		runner.cancel();
	}

	/**
	 * overwrite this method for custom stuff happening.
	 */
	public void onRun() {

	}

	public int getTimeout() {
		return timeout;
	}
}
