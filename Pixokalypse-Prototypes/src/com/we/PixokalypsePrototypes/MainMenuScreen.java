package com.we.PixokalypsePrototypes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainMenuScreen implements Screen {
	
	final PixokalypsePrototypes game;
 	OrthographicCamera camera;
	Stage stage;
	
	public MainMenuScreen(final PixokalypsePrototypes gam) {
		game = gam;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 960, 640);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
		
		game.batch.begin();		
		game.font24.draw(game.batch, "Pixokalypse Prototypes ", 100, 150);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		Gdx.input.setInputProcessor(stage);

		//Buttons erzeugen
		final TextButton button = new TextButton("  PotentialFieldTest  ", game.skin);
		button.setPosition(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/10*9)-Gdx.graphics.getWidth()/5/2);
		button.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/10);
		
		final TextButton button2 = new TextButton("  Nix  ", game.skin);
		button2.setPosition(Gdx.graphics.getWidth()/2, (Gdx.graphics.getHeight()/10*7)-Gdx.graphics.getWidth()/5/2);
		button2.setSize(Gdx.graphics.getWidth()/5, Gdx.graphics.getHeight()/10);
		
		//Buttons der Stage hinzufügen damit sie gerendert werden.
		stage.addActor(button);
		stage.addActor(button2);
		
		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		button.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				game.setScreen(new PotentialFieldTestScreen(game));
				dispose();
			}
		});
		
		button2.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
			}
		});
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}