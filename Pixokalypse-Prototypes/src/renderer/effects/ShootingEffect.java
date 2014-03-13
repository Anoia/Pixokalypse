package renderer.effects;

import util.Utils;

import agents.Agent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ShootingEffect extends Effect{
	
	float startX, startY, endX, endY;
	Texture shotTexture;
	float alpha = 1f;
	
	public ShootingEffect(float startX, float startY, float endX, float endY){
		this.startX = startX;
		this.startY = startY-4;
		this.endX = endX;
		this.endY = endY-4;
		shotTexture = new Texture(Gdx.files.internal("data/shot.png"));
		displayDuration = 0.25f;
    	remainingDisplayDuration = displayDuration;
	}
	
	public ShootingEffect(Agent start, Agent goal){
		this(start.x, start.y, goal.x, goal.y);
	}

	
    @Override
	public void tick(float delta){
		remainingDisplayDuration -= delta;
		if(remainingDisplayDuration <= 0){
			setDead(true);
		}
		if(alpha >= 0.1){
			alpha -= 0.1;
		}
	}
	
	@Override
	public void render(SpriteBatch batch, BitmapFont font) {
		int d = (int)Utils.getDistance(startX, startY, endX, endY);
		int h = shotTexture.getHeight();
		Sprite sprite = new Sprite(shotTexture, 0, 0, d, h);
		sprite.setOrigin(0, h/2);
		sprite.setPosition(startX, startY);
		float degrees = (float) Math.toDegrees(Math.atan2(endY - startY, endX - startX)); 
		//System.out.println("degrees: " + degrees);
		sprite.setRotation(degrees);
		sprite.setColor(0f, 0f, 0f, alpha);
		sprite.draw(batch);
	}

}