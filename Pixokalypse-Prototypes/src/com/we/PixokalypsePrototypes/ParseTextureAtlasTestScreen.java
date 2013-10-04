package com.we.PixokalypsePrototypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.we.PixokalypsePrototypes.test.SpriteContainer;

public class ParseTextureAtlasTestScreen implements Screen{ //,InputProcessor {
 final PixokalypsePrototypes game;
	
 private OrthographicCamera camera;
	private SpriteContainer spriteContainer;
	
	public ParseTextureAtlasTestScreen(final PixokalypsePrototypes gam) {
		this.game = gam;
		spriteContainer = new SpriteContainer();
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(false, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
//		angeblich für transparenz notwendig
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		//Sprites Rendern anfang
		
		Sprite sprite = spriteContainer.getSprite("hC2");
		float laufI = 40;
		float laufJ = 41;

		for (int i = 0;i < laufI;i++){
			for (int j = 1;j  < laufJ;j++){		
			sprite.setPosition(Gdx.graphics.getWidth()/laufI*i,Gdx.graphics.getHeight()/laufJ*j);
			sprite.draw(game.batch);
			}
		}
		
		//Sprites Rendern ende
		game.batch.end();

		
//      angeblich für transparenz notwendig ende
		Gdx.gl.glDisable(GL10.GL_BLEND);
		

		//FPS unten links rendern
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		game.font12.draw(game.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 20, 13);
		game.font12.draw(game.batch, "Anz gerenderte Tiles pro Frame: " + (int)(laufI * (laufJ-1)), Gdx.graphics.getWidth()/2, 13);
		game.batch.end();
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}