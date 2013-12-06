package input;

import potentialField.PotentialFieldManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class GameInputProcessor implements InputProcessor{

	private PotentialFieldManager manager;
	private Camera camera;
	private boolean zeichnen;
	
	public GameInputProcessor(PotentialFieldManager potentialFieldManager, Camera camera) {
		this.manager = potentialFieldManager;
		this.camera = camera;
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		System.out.println("KLICK:" + screenX +" "+ screenY);
		//UNPROJECT for real world coords
		
		Vector3 v = new Vector3(screenX, screenY, 0);
        camera.unproject(v);
        System.out.println("projected: " + v.x + " "+v.y);
		
		if(button == 0)manager.setPlayerCharacterTarget((int)v.x, (int)v.y);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
