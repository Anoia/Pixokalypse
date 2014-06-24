package renderer.effects;

import util.Utils;

import agents.Agent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public class ShootingEffect extends Effect{
	
	Agent start, end;
	
	static Texture shotTexture = new Texture(Gdx.files.internal("data/shot.png"));
	float alpha = 1f;
	
	public ShootingEffect(float startX, float startY, float endX, float endY){
		
		displayDuration = 0.25f;
    	remainingDisplayDuration = displayDuration;
	}
	
	public ShootingEffect(Agent start, Agent end){
		this.start = start;
		this.end = end;
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
	public void render(SpriteBatch batch, BitmapFont font, OrthographicCamera camera) {
		
		Vector3 startVector = new Vector3(start.x, start.y-4, 0);
		Vector3 endVector = new Vector3(end.x, end.y-4, 0);
		camera.project(startVector);
		camera.project(endVector);		
		
		
		int d = (int)Utils.getDistance(startVector.x, startVector.y, endVector.x, endVector.y);
		int h = shotTexture.getHeight();
		Sprite sprite = new Sprite(shotTexture, 0, 0, d, h);
		sprite.setOrigin(0, h/2);
		sprite.setPosition(startVector.x, startVector.y);
		float degrees = (float) Math.toDegrees(Math.atan2(endVector.y - startVector.y, endVector.x - startVector.x)); 
		//System.out.println("degrees: " + degrees);
		sprite.setRotation(degrees);
		sprite.setColor(0f, 0f, 0f, alpha);
		sprite.draw(batch);
	}

}
