package renderer.effects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Effect {

	/*
	 * Alle Effekte die ganz oben gerendert werden sollen und nach einer
	 * bestimmten Zeit verschwinden z.B. Dmgzahlen, Bullets
	 */

	protected boolean dead = false;
	protected float displayDuration = 1f;

	protected float remainingDisplayDuration = 1f;

	public boolean isDead() {
		return dead;
	}

	public abstract void render(SpriteBatch batch, BitmapFont font,
			OrthographicCamera camera);

	protected void setDead(boolean dead) {
		this.dead = dead;
	}

	public void tick(float delta) {
		remainingDisplayDuration -= delta;
		if (remainingDisplayDuration <= 0) {
			setDead(true);
		}
	}

}
