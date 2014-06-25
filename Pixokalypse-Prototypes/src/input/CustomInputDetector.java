package input;

import com.badlogic.gdx.input.GestureDetector;

/**
 * This Class is just for custom Input Detection. Input handling should only happen in the CustomInputHandler. 
 *
 */

public class CustomInputDetector extends GestureDetector {
	
	CustomInputHandler listener;

	public CustomInputDetector(CustomInputHandler listener) {
		super(listener);
		this.listener = listener;
	}
	
	@Override
	public boolean keyTyped (char character) {
		return listener.keyTyped(character);
	}
	
	@Override
	public boolean keyDown (int keycode) {
		return listener.keyDown(keycode);
	}

	@Override
	public boolean keyUp (int keycode) {
		return listener.keyUp(keycode);
	}
	
	@Override
	public boolean mouseMoved (int screenX, int screenY) {
		return listener.mouseMoved(screenX, screenY);
	}

	@Override
	public boolean scrolled (int amount) {
		return listener.scrolled(amount);
	}

}
