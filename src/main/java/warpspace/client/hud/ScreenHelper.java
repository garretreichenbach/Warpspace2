package warpspace.client.hud;

import warpspace.WarpSpace;

import javax.vecmath.Vector3f;
import java.awt.*;

/**
 * Helpers for converting between relative (percentage of screen) and
 * absolute (pixel) screen coordinates.
 */
public final class ScreenHelper {

	private ScreenHelper() {
	}

	/**
	 * Adjust given position (in fraction-of-screen, e.g. 0.5,0.5,0 = center)
	 * to current resolution pixels. Creates a new vector.
	 */
	public static Vector3f relPosToPixelPos(Vector3f pos) {
		return relPosToPixelPos(pos, false);
	}

	/**
	 * Convert fractional-screen position to pixel position.
	 *
	 * @param onlyWidth if true, uses only screen height for both x and y (preserves square aspect on resolution changes).
	 */
	public static Vector3f relPosToPixelPos(Vector3f pos, boolean onlyWidth) {
		Vector3f screenRes = getCurrentScreenResolution();
		if(onlyWidth) {
			screenRes = new Vector3f(screenRes.y, screenRes.y, 0);
		}
		Vector3f screenPos = new Vector3f(pos);
		scaleMultiply(screenPos, screenRes);
		return screenPos;
	}

	/**
	 * Convert pixel position to fractional-screen position.
	 *
	 * @param onlyWidth if true, uses only screen height for both x and y (preserves square aspect on resolution changes).
	 */
	public static Vector3f pixelPosToRelPos(Vector3f pos, boolean onlyWidth) {
		Vector3f screenRes = getCurrentScreenResolution();
		if(onlyWidth) {
			screenRes = new Vector3f(screenRes.y, screenRes.y, 0);
		}
		Vector3f screenPos = new Vector3f(pos);
		scaleDivide(screenPos, screenRes);
		return screenPos;
	}

	/**
	 * @return current screen resolution as (width, height, 0), or null if no graphics device.
	 */
	public static Vector3f getCurrentScreenResolution() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		if(gd == null) {
			WarpSpace.getInstance().logWarning("graphics device is null");
			return null;
		}
		return new Vector3f(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight(), 0);
	}

	/**
	 * Component-wise multiply: a = (a.x * b.x, a.y * b.y, a.z). Mutates a.
	 */
	public static void scaleMultiply(Vector3f a, Vector3f b) {
		a.set(a.x * b.x, a.y * b.y, a.z);
	}

	/**
	 * Component-wise divide: a = (a.x / b.x, a.y / b.y, a.z). Mutates a.
	 */
	public static void scaleDivide(Vector3f a, Vector3f b) {
		a.set(a.x / b.x, a.y / b.y, a.z);
	}

	/**
	 * @return new vector from point a to point b.
	 */
	public static Vector3f getDirection(Vector3f a, Vector3f b) {
		Vector3f direction = new Vector3f(b);
		direction.sub(a);
		return direction;
	}

	/**
	 * @return Euclidean distance between two points.
	 */
	public static float getDistance(Vector3f a, Vector3f b) {
		return getDirection(a, b).length();
	}
}
