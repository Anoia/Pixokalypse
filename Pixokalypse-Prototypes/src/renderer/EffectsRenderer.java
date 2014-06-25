package renderer;

import renderer.effects.Effect;
import screens.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EffectsRenderer {

	private SpriteBatch batch;
	private OrthographicCamera camera;
	public BitmapFont font12, font24;

	private GameScreen game;

	public EffectsRenderer(GameScreen game, SpriteBatch batch,
			OrthographicCamera camera) { // ,OrthographicCamera camera){
		this.game = game;
		this.batch = batch;
		this.camera = camera;
	}

	private void renderEffects() {
		for (Effect e : game.getRenderEffects()) {
			e.render(batch, font12, camera);
		}
	}

	public void setFonts(BitmapFont font12, BitmapFont font24) {
		this.font12 = font12;
		this.font24 = font24;
	}

	public void update(float delta) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
				Gdx.graphics.getHeight());
		batch.begin();
		font12.draw(batch, "fps: " + Gdx.graphics.getFramesPerSecond(), 30,
				Gdx.graphics.getHeight() - 30);
		renderEffects();
		batch.end();
	}

}
