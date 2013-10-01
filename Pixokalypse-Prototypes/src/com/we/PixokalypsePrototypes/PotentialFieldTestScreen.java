package com.we.PixokalypsePrototypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class PotentialFieldTestScreen implements Screen{ //,InputProcessor {
 final PixokalypsePrototypes game;
	private OrthographicCamera camera;

	private Texture texture;
	private Sprite sprite;

	public PotentialFieldTestScreen(final PixokalypsePrototypes gam) {
		this.game = gam;
		
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();		
		
		

		//Menüelemente in Textur zeichnen
		Pixmap pm = new Pixmap(w, h, Format.RGBA4444);
		pm.setColor(0f, 0f, 0f,1f);
		

		//Heightmap
		pm.fillRectangle(0, 10, 10, 10);

		texture = new Texture(gam.getNextPowOf2(w), gam.getNextPowOf2(h), Format.RGBA4444);
		texture.draw(pm, 50, 50);
		pm.dispose();
		
		//texture = new Texture(Gdx.files.internal("data/libgdx.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, w, h);
		sprite = new Sprite(region);
		sprite.setPosition(0, 0);		
	}
	
	@Override
	public void dispose() {
		texture.dispose();
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
		sprite.draw(game.batch);
		game.batch.end();
		
//      angeblich für transparenz notwendig ende
		Gdx.gl.glDisable(GL10.GL_BLEND);
		

		//FPS unten links rendern
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		game.font12.draw(game.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 5, 5);
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