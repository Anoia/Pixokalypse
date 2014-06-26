package input;

import screens.GameScreen;
import util.Constants;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.we.PixokalypsePrototypes.test.Field;
import com.we.PixokalypsePrototypes.test.FieldCategory;

public class CustomInputHandler implements GestureListener{
	
	GameScreen game;
	
	public CustomInputHandler(GameScreen game){
		this.game = game;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button){ 
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// unproject to transform screen coords to real world coords
		Vector3 v = new Vector3(x, y, 0);
		game.getCamera().unproject(v);

		switch (button) {
			case Buttons.LEFT:
				Field field = game.getMap().data[(int)(v.x/Constants.TILE_SIZE)][(int)(v.y/Constants.TILE_SIZE)];
				int value = game.getPotentialFieldManager().getEnvironmentMap().fieldArray[(int) v.x][(int) v.y];
				if(value >= 10000){
					if(field.fieldCategory == FieldCategory.BUILDING){
						if(field.entered){
							System.out.println("You already visited this building. Stuff already happened.");
						}else{
							game.enterBuilding(field);
						}
						
					}
				}else{
					game.getPotentialFieldManager().setPlayerCharacterTarget((int) v.x, (int) v.y);
				}
				break;
			case Buttons.RIGHT:
				break;
			case Buttons.MIDDLE:
				break;
		}
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		System.out.println("LOOOONG Klick!");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//game.getCamera().translate(-deltaX*0.2f, -deltaY*0.2f);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		return false;
	}
	
	public boolean keyTyped (char character) {
		return false;
	}
	
	public boolean keyDown (int keycode) {
		return false;
	}

	public boolean keyUp (int keycode) {
		return false;
	}
	
	public boolean mouseMoved (int screenX, int screenY) {
		return false;
	}

	public boolean scrolled (int amount) {
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

}
