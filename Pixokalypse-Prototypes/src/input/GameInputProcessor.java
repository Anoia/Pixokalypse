package input;

import potentialField.PotentialFieldManager;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class GameInputProcessor implements InputProcessor{

	private PotentialFieldManager manager;
	private Camera camera;
	
	public GameInputProcessor(PotentialFieldManager potentialFieldManager, Camera camera) {
		this.manager = potentialFieldManager;
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		// unproject to transform screen coords to real world coords
		Vector3 v = new Vector3(screenX, screenY, 0);
        camera.unproject(v);

		switch(button){
		case Buttons.LEFT:
			manager.setPlayerCharacterTarget((int)v.x, (int)v.y);
			break;
		case Buttons.RIGHT:
			break;
		case Buttons.MIDDLE:
			break;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
