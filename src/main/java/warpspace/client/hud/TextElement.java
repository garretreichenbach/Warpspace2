package warpspace.client.hud;

import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.schema.schine.graphicsengine.forms.gui.GUITextOverlay;
import org.schema.schine.input.InputState;

/**
 * blatantly stolen from pilotelement of star api
 */
public class TextElement extends GUITextOverlay {
	public String text = "";
	public HUDElement parent;
	public TextElement(UnicodeFont unicodeFont, InputState inputState) {
		super(50, 30, unicodeFont, Color.green, inputState);
	}

	@Override
	public void onInit() {
		super.onInit();
		setTextSimple(new Object() {
			@Override
			public String toString() {
				return text;
			}
		});
	}

	@Override
	public void draw() {
		if(parent == null) {
			return;
		}
		setPos(parent.getTextElementPxPos());
		super.draw();
	}
}
