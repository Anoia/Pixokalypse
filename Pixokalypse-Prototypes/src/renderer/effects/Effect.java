package renderer.effects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Effect {
	
	/* Alle Effekte die ganz oben gerendert werden sollen und nach einer bestimmten Zeit verschwinden
	 * z.B. Dmgzahlen, Bullets */
	
	protected float displayDuration = 1f;
	protected float remainingDisplayDuration = 1f;
	
	protected boolean dead = false;
	
	public void tick(float delta){
		remainingDisplayDuration -= delta;
		if(remainingDisplayDuration <= 0){
			setDead(true);
		}
	}
	
	public abstract void render(SpriteBatch batch, BitmapFont font, OrthographicCamera camera);

	public boolean isDead() {
		return dead;
	}

	protected void setDead(boolean dead) {
		this.dead = dead;
	}

}
