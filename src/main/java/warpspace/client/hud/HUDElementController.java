package warpspace.client.hud;

public class HUDElementController {
	/**
	 * will enable the given element
	 *
	 * @param element Spritelist element = image
	 * @param clean   disable other elements of this type
	 */
	public static void drawElement(SpriteList element, boolean clean) { //TODO is this used?
		if(clean) {
			//clean elements of same type
			//TODO write good find method
			for(HUDElement el : HUDCore.elementList) {
				if(el.enumValue == null) {
					continue;
				}
				if(el.enumValue.equals(element)) {
					clearType(el.type);
				}
			}
		}
		HUDCore.drawList.put(element, 1);
	}

	/**
	 * will set all elements of given type to ON: 1 or OFF: 2
	 *
	 * @param type  Elementtype
	 * @param value value 0,1
	 */
	public static void drawType(HUDElement.ElementType type, int value) {
		for(HUDElement el : HUDCore.elementList) {
			if(el.type == null) {
				continue;
			}
			if(el.type.equals(type)) {
				HUDCore.drawList.put(el.enumValue, value);
			}
		}
	}

	/**
	 * will clear all elements of this type from screen
	 *
	 * @param type ElementType to clear.
	 */
	public static void clearType(HUDElement.ElementType type) {
		drawType(type, 0);
	}
}
