package com.we.PixokalypsePrototypes;

import com.badlogic.gdx.InputProcessor;
import com.we.PixocalypsePrototypes.PotentialField.PotentialFieldManager;

public class PotentialFieldInputProcessor implements InputProcessor {
	private PotentialFieldManager manager;
	private boolean zeichnen;

	public PotentialFieldInputProcessor(PotentialFieldManager manager) {
		this.manager = manager;
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
		System.out.println(screenX +" "+ screenY);
		//UNPROJECT for real world coords
		if(button == 0)manager.setPlayerCharacterTarget(screenX, screenY);
		if(button == 1){
			manager.drawOnEnvironmentMap(screenX,screenY);
			zeichnen = true;
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(button == 1){
			zeichnen = false;
		}		
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(zeichnen)manager.drawOnEnvironmentMap(screenX,screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		if(zeichnen)manager.drawOnEnvironmentMap(screenX,screenY);
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
