package renderer.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextEffect extends Effect {
	public float alpha;
	public Color color;
	public String text;
	public float x;
	public float y;

	public TextEffect(float x, float y, String text) {
		this(x, y, text, new Color(1f, 0f, 0f, 1f));
	}

	public TextEffect(float x, float y, String text, Color color) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.color = color;
		displayDuration = 0.5f;
		remainingDisplayDuration = displayDuration;
	}

	@Override
	public void render(SpriteBatch batch, BitmapFont font,
			OrthographicCamera camera) {
		font.setColor(color);
		font.draw(batch, text, x, y);
	}

	@Override
	public void tick(float delta) {
		remainingDisplayDuration -= delta;
		if (remainingDisplayDuration <= 0) {
			setDead(true);
		}
		if (remainingDisplayDuration < displayDuration / 4) {
			alpha -= delta;
			color = new Color(color.r, color.g, color.b, alpha);
			y += 1f;
		}
	}

}
