package com.we.PixokalypsePrototypes;

import Agents.PlayerCharacter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.we.PixocalypsePrototypes.PotentialField.PotentialFieldManager;
import com.we.PixocalypsePrototypes.PotentialField.StaticPotentialField;

public class PotentialFieldTestScreen implements Screen{ //,InputProcessor {
	final PixokalypsePrototypes game;
	private OrthographicCamera camera;
	private PotentialFieldManager manager;
	private PlayerCharacter player;
	private PlayerCharacter player2;
	private PlayerCharacter player3;
	private PlayerCharacter player4;
	private PlayerCharacter player5;
	
	

	public PotentialFieldTestScreen(final PixokalypsePrototypes gam) {
		this.game = gam;
		manager = new PotentialFieldManager(new StaticPotentialField(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
		Gdx.input.setInputProcessor(new PotentialFieldInputProcessor(manager));
		player = new PlayerCharacter(100, 100);
		manager.addPlayerCharacter(player);
		player2 = new PlayerCharacter(150, 100);
		manager.addPlayerCharacter(player2);
		player3 = new PlayerCharacter(180, 100);
		manager.addPlayerCharacter(player3);
		player4 = new PlayerCharacter(150, 130);
		manager.addPlayerCharacter(player4);
		player5 = new PlayerCharacter(150, 80);
		manager.addPlayerCharacter(player5);
		
	}
	
	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		camera = new OrthographicCamera(width, height);
		camera.setToOrtho(true, width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void render(float delta) {
		manager.step(delta);
		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		
		
//		angeblich für transparenz notwendig
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		//Sprites Rendern anfang
		
		//Sprites Rendern ende
		game.batch.end();
		
		//Shape rendern
		game.shapeRenderer.setProjectionMatrix(camera.combined);
		game.shapeRenderer.begin(ShapeType.Filled);
		game.shapeRenderer.setColor(Color.RED);
		game.shapeRenderer.rect(player.x-3, player.y-3, 7, 7);
		game.shapeRenderer.setColor(Color.GREEN);
		game.shapeRenderer.rect(player2.x-3, player2.y-3, 7, 7);
		game.shapeRenderer.setColor(Color.BLUE);
		game.shapeRenderer.rect(player3.x-3, player3.y-3, 7, 7);
		game.shapeRenderer.setColor(Color.ORANGE);
		game.shapeRenderer.rect(player4.x-3, player4.y-3, 7, 7);
		game.shapeRenderer.setColor(Color.DARK_GRAY);
		game.shapeRenderer.rect(player5.x-3, player5.y-3, 7, 7);

		game.shapeRenderer.end();
		
		
//      angeblich für transparenz notwendig ende
		Gdx.gl.glDisable(GL10.GL_BLEND);
		

		//FPS unten links rendern
		game.batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//font.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		game.batch.begin();
		game.font12.draw(game.batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
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