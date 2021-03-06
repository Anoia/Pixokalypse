package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.we.PixokalypsePrototypes.PixokalypsePrototypes;

public class MainMenuScreen implements Screen {

	OrthographicCamera camera;
	PixokalypsePrototypes game;
	Stage stage;

	public MainMenuScreen(PixokalypsePrototypes game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 960, 640);
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
	public void resume() {
	}

	@Override
	public void show() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		// Buttons erzeugen
		final TextButton button = new TextButton("  PotentialFieldTest  ",
				game.skin);
		button.setPosition(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() / 10 * 9) - Gdx.graphics.getWidth()
						/ 5 / 2);
		button.setSize(Gdx.graphics.getWidth() / 5,
				Gdx.graphics.getHeight() / 10);

		final TextButton button2 = new TextButton("  GameTest  ", game.skin);
		button2.setPosition(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() / 10 * 7) - Gdx.graphics.getWidth()
						/ 5 / 2);
		button2.setSize(Gdx.graphics.getWidth() / 5,
				Gdx.graphics.getHeight() / 10);

		final TextButton button3 = new TextButton("  SpriteParser  ", game.skin);
		button3.setPosition(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() / 10 * 5) - Gdx.graphics.getWidth()
						/ 5 / 2);
		button3.setSize(Gdx.graphics.getWidth() / 5,
				Gdx.graphics.getHeight() / 10);

		final TextButton button4 = new TextButton(
				"  collisionMap+PotentialFields  ", game.skin);
		button4.setPosition(Gdx.graphics.getWidth() / 2,
				(Gdx.graphics.getHeight() / 10 * 3) - Gdx.graphics.getWidth()
						/ 5 / 2);
		button4.setSize(Gdx.graphics.getWidth() / 5,
				Gdx.graphics.getHeight() / 10);

		// Buttons der Stage hinzuf�gen damit sie gerendert werden.
		stage.addActor(button);
		stage.addActor(button2);
		stage.addActor(button3);
		stage.addActor(button4);

		// Add a listener to the button. ChangeListener is fired when the
		// button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the
		// event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked.
		// Also, canceling a ClickListener event won't
		// revert the checked state.
		button.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new PotentialFieldTestScreen(game));
				dispose();
			}
		});

		button2.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new GameScreen(game));
				dispose();
			}
		});

		button3.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new ParseTextureAtlasTestScreen(game));
				dispose();
			}
		});
		button4.addListener(new ChangeListener() {
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new PotentialFieldMapTestScreen(game));
				dispose();
			}
		});
	}
}